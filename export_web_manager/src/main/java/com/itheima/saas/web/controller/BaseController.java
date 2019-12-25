package com.itheima.saas.web.controller;

import com.itheima.saas.domain.system.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author wangxin
 * @date 2019/12/18 18:07
 * @description: TODO
 * GOOD LUCK！
 * 抽取父类Controller
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    @Autowired
    protected HttpSession session;
    protected String companyName;
    protected String companyId;

    @ModelAttribute
    public void init(HttpServletResponse response) {
        this.response = response;
        User user = (User) session.getAttribute("loginUser");

        if (user != null) {
            this.companyId = user.getCompanyId();
            this.companyName = user.getCompanyName();
        }
        /*//测试数据
        this.companyId = "1";
        this.companyName = "传智播客教育股份有限公司";*/
    }

}
