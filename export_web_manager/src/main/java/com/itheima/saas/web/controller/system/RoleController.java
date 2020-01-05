package com.itheima.saas.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.Role;
import com.itheima.saas.service.stat.system.IModuleService;
import com.itheima.saas.service.stat.system.IRoleService;
import com.itheima.saas.service.stat.system.IUserService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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

    @Autowired
    private IModuleService moduleService;

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


    @RequestMapping(value = "/roleModule", name = "跳转角色分配页面")
    public String roleModule(String roleid) {
        Role role = roleService.findById(roleid);
        request.setAttribute("role", role);
        return "system/role/role-module";
    }

    //初始化模块数据
    @RequestMapping(value = "/initModuleData", name = "初始化模块数据")
    public @ResponseBody
    List<Map> initModuleData(String id) {
        //查询所有模块
        List<Module> moduleList = moduleService.findAll();
        //通过roleId查询该角色的模块列表
        List<Module> roleModule = moduleService.findByRoleId(id);
        //构建前端需要的list
        List<Map> list = new ArrayList<>();
        for (Module module : moduleList) {
            Map map = new HashMap();
            map.put("id", module.getId());
            map.put("pId", module.getParentId());
            map.put("name", module.getName());
            //如果该角色的module在modulelist中存在 则添加选中
            if (roleModule.contains(module)) {
                map.put("checked", true);
            }
            list.add(map);
        }
        return list;
    }

    @RequestMapping(value = "/updateRoleModule", name = "修改用户角色对应的模块")
    public String updateRoleModule(String roleid, String moduleIds) {
        moduleService.updateRoleModule(roleid,moduleIds);
        return "redirect:/system/role/list.do";
    }

}
