package com.itheima.saas.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.Role;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.system.IRoleService;
import com.itheima.saas.service.system.IUserService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author wangxin
 * @date 2019/12/22 00:37
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/system/role")
public class RoleController extends BaseController {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/list", name = "分页查询角色列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        PageInfo<Role> pageInfo = roleService.findByPage(page, size, companyId);
        request.setAttribute("page", pageInfo);
        return "system/role/role-list";
    }


    @RequestMapping(value = "/toAdd", name = "跳转角色添加页面")
    public String toAdd() {
        return "system/role/role-add";
    }

    @RequestMapping(value = "/edit", name = "编辑角色页面")
    public String edit(Role role) {
        role.setCompanyId(companyId);
        role.setCompanyName(companyName);
        if (StringUtils.isEmpty(role.getId())) {
            role.setId(UUID.randomUUID().toString());
            roleService.save(role);
        } else {
            roleService.update(role);
        }
        return "redirect:/system/role/list.do";
    }


    @RequestMapping(value = "/toUpdate", name = "跳转到修改页面")
    public String toUpdate(String id) {
        //根据id查询角色
        Role role = roleService.findById(id);
        request.setAttribute("role", role);
        return "system/role/role-update";
    }

    @RequestMapping(value = "/delete", name = "删除角色信息")
    public String delete(String id) {
        roleService.delete(id);
        return "redirect:/system/role/list.do";
    }


}
