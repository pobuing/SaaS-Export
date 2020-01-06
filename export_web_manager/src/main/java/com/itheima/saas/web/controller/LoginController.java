package com.itheima.saas.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.itheima.saas.common.utils.OkHttpUtil;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IModuleService;
import com.itheima.saas.service.stat.system.IUserService;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 15:52
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
public class LoginController extends BaseController {
    public static final String AppID = "wx3bdb1192c22883f3";
    public static final String AppSecret = "db9d6b88821df403e5ff11742e799105";
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

    @RequestMapping(value = "/loginwx", name = "微信登录")
    public String wxLogin(String code, String state) {
        String weixin_auth_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + AppID + "&" +
                "secret=" + AppSecret + "&" +
                "code=" + code + "&" +
                "grant_type=authorization_code";
        String httpGet = OkHttpUtil.httpGet(weixin_auth_url);
        if (!StringUtils.isEmpty(httpGet)) {
            JSONObject jsonObject = JSONObject.parseObject(httpGet);
            System.out.println("-------" + jsonObject);
            String errcode = jsonObject.getString("errcode");
            if (!StringUtils.isEmpty(errcode)) {
                //出现errcode表示登录失败
                request.setAttribute("error", "登录失败！");
                return "forward:login.jsp";
            } else {
                //没有出现errorcode表示成功
                //从jsonObject中取出openId
                String openid = jsonObject.getString("openid");
                //根据openId查询用户
                User byOpenId = userService.findByOpenId(openid);
                System.out.println("微信登录查询到的用户" + byOpenId);
                //用户是否为空
                if (byOpenId == null) {
                    //为空跳转登录页面
                    request.setAttribute("error", "请您登录后自动绑定");
                    request.setAttribute("openid", openid);
                    return "forward:login.jsp";
                } else {
                    // 不为空直接登录
                    session.setAttribute("loginUser", byOpenId);
                    //登录成功后将对应用户的对应角色的模块信息放入到session中
                    List<Module> modules = moduleService.findByUser(byOpenId);
                    session.setAttribute("modules", modules);
                    //转发首页
                    return "home/main";

                }

            }
        } else {
            request.setAttribute("error", "登录失败！");
        }
        return "forward:login.jsp";
    }

    //退出
    @RequestMapping(value = "/logout", name = "用户登出")
    public String logOut() {
        SecurityUtils.getSubject().logout();
        return "forward:login.jsp";
    }
}
