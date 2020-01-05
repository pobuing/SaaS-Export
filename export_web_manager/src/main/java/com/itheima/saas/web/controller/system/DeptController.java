package com.itheima.saas.web.controller.system;

import com.itheima.saas.common.entity.PageBean;
import com.itheima.saas.domain.system.Dept;
import com.itheima.saas.service.stat.system.IDeptService;
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
 * @date 2019/12/21 12:17
 * @description: TODO
 * GOOD LUCK！
 */

@RequestMapping(value = "/system/dept")
@Controller
public class DeptController extends BaseController {

    @Autowired
    private IDeptService deptService;

    @RequestMapping(value = "/list", name = "分页查询部门列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        PageBean pageBean = deptService.findByPage(page, size, companyId);
        request.setAttribute("page", pageBean);
        return "system/dept/dept-list";
    }


    @RequestMapping(value = "/toAdd", name = "跳转部门添加页面")
    public String toAdd() {
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/dept/dept-add";
    }


    @RequestMapping(value = "/edit", name = "保存部门信息")
    public String edit(Dept dept) {
        dept.setCompanyId(companyId);
        dept.setCompanyName(companyName);
        if (StringUtils.isEmpty(dept.getId())) {
            //新建 新建的dept id为空
            dept.setId(UUID.randomUUID().toString());
            deptService.save(dept);
        } else {
            deptService.update(dept);
        }
        //转发回页面列表
        return "redirect:/system/dept/list.do";
    }


    @RequestMapping(value = "/toUpdate", name = "跳转去修改页面")
    public String toUpdate(String id) {
        //根据id查询出对应的dept
        Dept dept = deptService.findById(id);
        request.setAttribute("dept", dept);
        //修改时 需要上级目录列表
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/dept/dept-update";
    }


    @RequestMapping(value = "/delete", name = "删除部门信息")
    public String delete(String id) {
        deptService.delete(id);
        //重定向回列表页面
        return "redirect:/system/dept/list.do";

    }
}
