package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.domain.vo.ExportProductVo;
import com.itheima.saas.domain.vo.ExportResult;
import com.itheima.saas.domain.vo.ExportVo;
import com.itheima.saas.service.stat.cargo.ContractService;
import com.itheima.saas.service.stat.cargo.ExportProductService;
import com.itheima.saas.service.stat.cargo.ExportService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.cxf.jaxrs.client.WebClient;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/1 13:11
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/cargo/export")
public class ExportController extends BaseController {

    @Reference
    private ContractService contractService;
    @Reference
    private ExportService exportService;

    @Reference
    private ExportProductService exportProductService;


    @RequestMapping(value = "/contractList")
    public String contractList(@RequestParam(defaultValue = "1") int page,
                               @RequestParam(defaultValue = "10") int size) {
        ContractExample example = new ContractExample();
        ContractExample.Criteria criteria = example.createCriteria();
        criteria.andStateEqualTo(1);
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = contractService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/export/export-contractList";
    }

    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size) {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/export/export-list";
    }

    @RequestMapping(value = "/toExport")
    public String toExport(String id) {
        request.setAttribute("id", id);
        return "cargo/export/export-toExport";
    }

    @RequestMapping(value = "/edit")
    public String edit(Export export) {

        if (StringUtil.isEmpty(export.getId())) {
            export.setId(UUID.randomUUID().toString());
            export.setCompanyId(companyId);
            export.setCompanyName(companyName);
            try {
                exportService.save(export);
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        } else {
            exportService.update(export);
        }
        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id) {
        Export export = exportService.findById(id);
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> list = exportProductService.findAll(example);
        //3.将export,eps放入request域当中
        request.setAttribute("export", export);
        request.setAttribute("eps", list);
        //4.跳转页面
        return "cargo/export/export-update";
    }

    @RequestMapping(value = "/exportE")
    public String exportE(String id) {
        //1.创建ExportVo实体类
        ExportVo exportVo = new ExportVo();
        //2.通过id查询export实体类
        Export export = exportService.findById(id);
        //3.将export实体类中的信息复制到ExportVo实体类中
        BeanUtils.copyProperties(export, exportVo);
        exportVo.setExportId(id);
        //4.创建ExportProductVo所在的列表
        List<ExportProductVo> exportProductVos = new ArrayList<>();
        //5.通过id查询exportProduct列表
        ExportProductExample example = new ExportProductExample();
        ExportProductExample.Criteria criteria = example.createCriteria();
        criteria.andExportIdEqualTo(id);
        List<ExportProduct> products = exportProductService.findAll(example);
        //6.循环报运商品列表
        for (ExportProduct product : products) {
            ExportProductVo exportProductVo = new ExportProductVo();
            //7.给ExportProductVo赋值
            BeanUtils.copyProperties(product, exportProductVo);
            exportProductVo.setExportId(id);
            exportProductVo.setExportProductId(product.getId());
            exportProductVos.add(exportProductVo);
        }
        //8.给ExportVo.products赋值
        exportVo.setProducts(exportProductVos);

        //System.out.println(exportVo);

        /**
         * http://192.168.167.64:8088/ws/export/user
         * http://192.168.167.64:8088/ws/export/user/{id}
         */

        //9.调用webservice接口
        WebClient wc = WebClient.create("http://192.168.167.64:8088/ws/export/user");
        wc.post(exportVo);

        wc = WebClient.create("http://192.168.167.64:8088/ws/export/user/" + id);
        ExportResult exportResult = wc.get(ExportResult.class);

        //System.out.println(exportResult);

        exportService.updateE(exportResult);

        return "redirect:/cargo/export/list.do";
    }

    @RequestMapping(value = "/toView")
    public String toView(String id) {
        Export export = exportService.findById(id);
        request.setAttribute("export", export);
        return "cargo/export/export-view";
    }
}
