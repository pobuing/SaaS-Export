package com.itheima.saas.web.controller.company;

import com.itheima.saas.domain.company.Company;
import com.itheima.saas.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/16 20:50
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/company")
public class CompanyController {
    @Autowired
    private ICompanyService companyService;

    @RequestMapping("/list")
    public String list(HttpServletRequest request) {
        List<Company> list = companyService.findAll();
        System.out.println(list);
        request.setAttribute("list", list);
        return "company/company-list";
    }


}
