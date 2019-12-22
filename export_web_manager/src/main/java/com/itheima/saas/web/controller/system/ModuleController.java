package com.itheima.saas.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.system.IModuleService;
import com.itheima.saas.service.system.IUserService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/22 00:59
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/system/module")
public class ModuleController extends BaseController {
    @Autowired
    private IModuleService moduleService;



    //分页查询模块列表
    @RequestMapping(value = "/list", name = "分页查询模块列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {

        PageInfo<Module> pageInfo = moduleService.findByPage(page, size);
        request.setAttribute("page", pageInfo);
        return "system/module/module-list";
    }

    @RequestMapping(value = "/toAdd", name = "跳转到添加模块页面")
    public String toAdd() {
        //查询上级模块
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus", list);
        return "system/module/module-add";
    }

    @RequestMapping(value = "/edit", name = "保存模块信息")
    public String edit(Module module) {
        if (StringUtils.isEmpty(module.getId())) {
            //为空表示新增
            module.setId(UUID.randomUUID().toString());
            moduleService.save(module);
        } else {
            moduleService.update(module);
        }
        return "redirect:/system/module/list.do";
    }

    @RequestMapping(value = "/toUpdate", name = "修改模块信息")
    public String toUpdate(String id) {
        List<Module> list = moduleService.findAll();
        request.setAttribute("menus", list);
        //通过id查询
        Module module = moduleService.findById(id);
        request.setAttribute("module", module);
        return "system/module/module-update";
    }

    @RequestMapping(value = "/delete", name = "删除模块信息")
    public String delete(String id) {
        moduleService.delete(id);
        return "redirect:/system/module/list.do";
    }


}
