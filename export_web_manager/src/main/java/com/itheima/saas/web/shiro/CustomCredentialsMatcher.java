package com.itheima.saas.web.shiro;

import com.itheima.saas.common.utils.Encrypt;
import com.itheima.saas.web.constant.LoginType;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

/**
 * @author wangxin
 * @date 2019/12/24 15:04
 * @description: TODO
 * GOOD LUCK！
 */
public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        EasyTypeToken upToke = (EasyTypeToken) token;
        //获取登录方式
        if (((EasyTypeToken) token).getLoginType()== LoginType.NOPASSWORD) {
            return true;
        }
        String userPassword = String.valueOf(upToke.getPassword());
        String email = upToke.getUsername();
        String md5Password = Encrypt.md5(userPassword, email);
//        获取数据库密码
        String dbPassword = String.valueOf(info.getCredentials());
        System.out.println("dbPassword = " + dbPassword);
        //加密后和数据库比较
        boolean b1 = md5Password.equals(dbPassword);
        //原本和数据库比较
        boolean b2 = userPassword.equals(dbPassword);
        return b1 || b2;
    }
}
