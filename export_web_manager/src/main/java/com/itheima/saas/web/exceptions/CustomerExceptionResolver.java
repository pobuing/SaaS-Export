package com.itheima.saas.web.exceptions;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangxin
 * @date 2019/12/18 11:54
 * @description: TODO
 * GOOD LUCK！
 * 异常处理
 */

public class CustomerExceptionResolver implements HandlerExceptionResolver {
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ex instanceof UnauthorizedException) {
            return new ModelAndView("forward:/unauthorized.jsp");
        }
        return new ModelAndView("error", "errorMsg", "sorry");
    }
}
