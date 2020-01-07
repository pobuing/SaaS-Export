package com.itheima.saas.web.controller.baseinfo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;
import com.itheima.saas.service.cargo.FactoryService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/7 14:20
 * @description: TODO
 * GOOD LUCK！
 */
@RequestMapping("/baseinfo/factoryinfo")
@Controller
public class FactoryInfoController extends BaseController {


    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        FactoryExample factoryExample = new FactoryExample();
        PageInfo pageInfo = factoryService.findByPage(factoryExample, page, size);
        request.setAttribute("page", pageInfo);
        return "system/factoryinfo/factory-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        return "system/factoryinfo/factory-add";
    }

    @RequestMapping("/toUpdate")
    public String toUpdate(String id) {
        Factory factory = factoryService.findById(id);
        request.setAttribute("factory", factory);
        return "system/factoryinfo/factory-add";
    }

    @RequestMapping("/edit")
    public String edit(Factory factory) {
        String factoryId = factory.getId();
        if (!StringUtils.isEmpty(factoryId)) {
            //表示修改
            factoryService.update(factory);
        } else {
            factory.setId(UUID.randomUUID().toString());
            factoryService.save(factory);
        }
        return "redirect:/baseinfo/factoryinfo/list.do";
    }

    @RequestMapping("/delete")
    public String delete(String id) {
        factoryService.delete(id);
        return "redirect:/baseinfo/factoryinfo/list.do";
    }

}
