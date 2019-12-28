package com.itheima.saas.web.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.saas.company.ICompanyService;
import com.itheima.saas.domain.company.Company;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/28 22:05
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
public class ApplyController {

    @Reference
    private ICompanyService companyService;

    @RequestMapping(value = "/apply")
    @ResponseBody
    public String apply(Company company) {
        System.out.println(company.toString());
        try {
            company.setId(UUID.randomUUID().toString());
            company.setState(0);
            companyService.save(company);
            return "1";
        } catch (Exception e) {
            return "2";
        }
    }
}
