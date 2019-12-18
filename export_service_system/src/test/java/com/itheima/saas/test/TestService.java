package com.itheima.saas.test;

import com.itheima.saas.dao.company.CompanyDao;
import com.itheima.saas.service.company.ICompanyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wangxin
 * @date 2019/12/16 19:53
 * @description: TODO
 * GOOD LUCK！
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext-*.xml")
public class TestService {
    @Autowired
    private ICompanyService companyService;

    @Test
    public void testService() {
        companyService.findAll().stream().forEach(company -> System.out.println(company));
    }

}
