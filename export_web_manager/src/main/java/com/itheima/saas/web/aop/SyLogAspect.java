package com.itheima.saas.web.aop;

import com.itheima.saas.domain.system.SysLog;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.system.ISysLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/23 16:44
 * @description: TODO
 * GOOD LUCK！
 */
@Aspect
@Component
public class SyLogAspect {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

    @Autowired
    private ISysLogService sysLogService;

    //代理方法
    @Around(value = "execution(* com.itheima.web.controller.*.*.*(..))")
    public Object saveLog(ProceedingJoinPoint pjp) throws Throwable {
        SysLog sysLog = new SysLog();
        sysLog.setId(UUID.randomUUID().toString());
        //获取登录的session
        User loginUser = (User) session.getAttribute("loginUser");
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        if (loginUser != null && requestMapping != null) {
            sysLog.setUserName(loginUser.getUserName());
            sysLog.setCompanyId(loginUser.getCompanyId());
            sysLog.setCompanyName(loginUser.getCompanyName());
            sysLog.setTime(new Date());
            sysLog.setIp(request.getLocalAddr());
            sysLog.setMethod(method.getName());
            sysLog.setAction(requestMapping.name());
            //将构造好的数据保存到数据库
            sysLogService.save(sysLog);
        }
        return pjp.proceed();
    }

}
