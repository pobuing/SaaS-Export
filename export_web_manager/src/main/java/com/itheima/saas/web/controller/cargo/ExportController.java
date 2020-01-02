package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.service.cargo.ContractService;
import com.itheima.saas.service.cargo.ExportProductService;
import com.itheima.saas.service.cargo.ExportService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.InvocationTargetException;
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
}
