package com.itheima.saas.web.controller;

import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IModuleService;
import com.itheima.saas.service.stat.system.IUserService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 15:52
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
public class LoginController extends BaseController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IModuleService moduleService;

    @RequestMapping("/home")
    public String home() {
        return "home/home";
    }

    @RequestMapping(value = "/login", name = "用户登录")
    public String login(String email, String password) {
        //判断参数
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            request.setAttribute("error", "用户名或密码不能为空");
            return "forward:login.jsp";
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(email, password);
            //将用户输入的信息加入身份认证
            subject.login(usernamePasswordToken);
            User user = (User) subject.getPrincipal();
//        通过邮箱查询数据库 得到用户信息
//        User user = userService.findByEmail(email);
            //登录成功 加入session中
            session.setAttribute("loginUser", user);
            //登录成功后将对应用户的对应角色的模块信息放入到session中
            List<Module> modules = moduleService.findByUser(user);
            session.setAttribute("modules", modules);
            //转发首页
            return "home/main";
        } catch (Exception e) {
            request.setAttribute("error", "用户名或密码不正确！");
            return "forward:login.jsp";
        }
    }

    //退出
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logOut() {
        SecurityUtils.getSubject().logout();
        return "forward:login.jsp";
    }
}
