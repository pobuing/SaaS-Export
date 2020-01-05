package com.itheima.saas.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.common.utils.Encrypt;
import com.itheima.saas.common.utils.MailUtil;
import com.itheima.saas.domain.system.Dept;
import com.itheima.saas.domain.system.Role;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IDeptService;
import com.itheima.saas.service.stat.system.IRoleService;
import com.itheima.saas.service.stat.system.IUserService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/21 20:58
 * @description: TODO
 * GOOD LUCK！
 */
@RequestMapping("/system/user")
@Controller
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IDeptService deptService;
    @Autowired
    private IRoleService roleService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping(value = "/list", name = "分页查询用户列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        PageInfo<User> pageInfo = userService.findAll(page, size, companyId);
        request.setAttribute("page", pageInfo);
        return "/system/user/user-list";
    }


    @RequestMapping(value = "/toAdd", name = "跳转用户新建页面")
    public String toAdd() {
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/user/user-add";
    }


    @RequestMapping(value = "/edit", name = "编辑用户")
    public String edit(User user) {
        user.setCompanyId(companyId);
        user.setCompanyName(companyName);
        if (StringUtils.isEmpty(user.getId())) {
            //新增设置id
            user.setId(UUID.randomUUID().toString());
            user.setPassword(Encrypt.md5(user.getPassword(), user.getEmail()));
            //调用service 保存
            userService.save(user);
            String password = user.getPassword();
            //发送邮件
            String to = user.getEmail();
            String subject = "欢迎来到SaaS-Export大家庭";
            String content = "您使用的SaaS-Export平台通过：http://localhost:8080 进行登录，用户名为：" + to + "，密码是：" + password;
            try {
                MailUtil.sendMsg(to, subject, content);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //将邮件内容写入到rabbitMQ中

            /**
             * 两个参数：1、routingKey：队列名称，对应于配置文件中的队列名
             *          2、传入的发送的消息参数
             */

            Map map = new HashMap();
            map.put("to", to);
            map.put("subject", subject);
            map.put("content", content);

            amqpTemplate.convertAndSend("mail.send", map);
        } else {
            //修改
            userService.update(user);
        }
        //重定向回列表页
        return "redirect:/system/user/list.do";

    }


    @RequestMapping(value = "/toUpdate", name = "跳转到修改页面")
    public String toUpdate(String id) {
        //通过id查询用户
        User user = userService.findById(id);
        request.setAttribute("user", user);
        //查询部门列表
        List<Dept> deptList = deptService.findAll(companyId);
        request.setAttribute("deptList", deptList);
        return "system/user/user-update";
    }

    @RequestMapping(value = "/delete", name = "删除选中的用户")
    public String delete(String id) {
        userService.delete(id);
        return "redirect:/system/user/list.do";
    }

    @RequestMapping(value = "/roleList", name = "跳转用户角色分配页面")
    public String roleList(String id) {
        //根据id查询user信息
        User user = userService.findById(id);
        request.setAttribute("user", user);
        //找到所有角色信息
        List<Role> roleList = roleService.findAll(companyId);
        request.setAttribute("roleList", roleList);

        //通过用户查询本身具有的角色信息
        List<Role> userRole = roleService.findByUserId(id);
        String userRoleStr = "";
        for (Role role : userRole) {
            userRoleStr += userRoleStr + role.getId() + ",";
        }
        request.setAttribute("userRoleStr", userRoleStr);
        return "system/user/user-role";
    }

    @RequestMapping(value = "/changeRole", name = "保存用户角色")
    public String changeRole(String userid, String roleIds) {
        roleService.changeRole(userid, roleIds);
        return "redirect:/system/user/list.do";
    }

}
