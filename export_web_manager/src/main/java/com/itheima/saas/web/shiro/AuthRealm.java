package com.itheima.saas.web.shiro;

import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.system.IModuleService;
import com.itheima.saas.service.system.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wangxin
 * @date 2019/12/24 15:02
 * @description: TODO
 * GOOD LUCK！
 */
public class AuthRealm extends AuthorizingRealm {
    @Autowired
    private IUserService userService;
    @Autowired
    private IModuleService moduleService;

    /**
     * 授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User) principals.getPrimaryPrincipal();
        //通过user查询所有的模块信息
        List<Module> modules = moduleService.findByUser(user);
        Set<String> perm = new HashSet<>();
        for (Module module : modules) {
            perm.add(module.getName());
        }
        //返回的授权信息中包含了所有的模块名称
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(perm);
        return info;
    }

    /**
     * 身份认证
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        String email = upToken.getUsername();
        User user = userService.findByEmail(email);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), "AuthenticationInfo");
        return info;
    }
}
