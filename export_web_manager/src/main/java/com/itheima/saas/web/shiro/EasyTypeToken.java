package com.itheima.saas.web.shiro;

import com.itheima.saas.web.constant.LoginType;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author wangxin
 * @date 2020/1/7 12:30
 * @description: TODO
 * GOOD LUCK！
 * 自定义usernamepasswordtoken
 */
public class EasyTypeToken extends UsernamePasswordToken {
    private LoginType loginType;

    public EasyTypeToken() {
        super();
    }

    public EasyTypeToken(String username, String password, boolean rememberMe, String host, LoginType loginType) {
        super(username, password, rememberMe, host);
        this.loginType = loginType;
    }

    /**
     * 用户名和密码登录 正常登录
     * @param username
     * @param password
     */
    public EasyTypeToken(String username, String password) {
        super(username, password);
        this.loginType = LoginType.NORMAL;
    }

    /**
     * 免密登录
     * @param username
     */
    public EasyTypeToken(String username){
        super(username,"",false,null);
            this.loginType = LoginType.NOPASSWORD;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }
}
