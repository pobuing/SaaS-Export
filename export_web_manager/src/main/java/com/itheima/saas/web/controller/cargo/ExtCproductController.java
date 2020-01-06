package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.common.utils.UploadUtil;
import com.itheima.saas.domain.cargo.ExtCproduct;
import com.itheima.saas.domain.cargo.ExtCproductExample;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;
import com.itheima.saas.service.cargo.ExtCproductService;
import com.itheima.saas.service.cargo.FactoryService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/30 19:54
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/cargo/extCproduct")
public class ExtCproductController extends BaseController {
    @Reference
    private FactoryService factoryService;

    @Reference
    private ExtCproductService extCproductService;

    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size,
                       String contractId, String contractProductId) {

        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> list = factoryService.findAll(factoryExample);
        //4. 将货物厂家信息放入request域当中
        request.setAttribute("factoryList", list);
        ExtCproductExample extCproductExample = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = extCproductExample.createCriteria();
        criteria.andContractProductIdEqualTo(contractProductId);
        PageInfo pageInfo = extCproductService.findAll(extCproductExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/extc/extc-list";
    }


    @RequestMapping(value = "/edit")
    public String edit(ExtCproduct extCproduct, MultipartFile productPhoto) {
        extCproduct.setCompanyId(companyId);
        extCproduct.setCompanyName(companyName);
        if (productPhoto != null) {
            UploadUtil uploadUtil = new UploadUtil();
            String upload = null;
            try {
                upload = uploadUtil.upload(productPhoto.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
            extCproduct.setProductImage(upload);
        }
        if (StringUtils.isEmpty(extCproduct.getId())) {
            extCproduct.setId(UUID.randomUUID().toString());
            //保存数据库
            extCproductService.save(extCproduct);
        } else {
            extCproductService.update(extCproduct);
        }
        return "redirect:/cargo/extCproduct/list.do?contractId=" +
                extCproduct.getContractId() + "&contractProductId=" + extCproduct.getContractProductId();
    }


    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id, String contractId, String contractProductId) {
        ExtCproduct extCproduct = extCproductService.findById(id);
        request.setAttribute("extCproduct", extCproduct);
        request.setAttribute("contractId", contractId);
        request.setAttribute("contractProductId", contractProductId);
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("附件");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        request.setAttribute("factoryList", factoryList);

        return "cargo/extc/extc-update";
    }


    @RequestMapping(value = "/delete")
    public String delete(String id, String contractId, String contractProductId) {
        extCproductService.delete(id);
        return "redirect:/cargo/extCproduct/list.do?contractId=" +
                contractId + "&contractProductId=" + contractProductId;
    }

}
