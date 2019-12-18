package com.itheima.saas.test;

import com.itheima.saas.dao.company.CompanyDao;
import com.itheima.saas.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/16 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext-*.xml")
public class TestEnvironment {
    @Autowired
    private CompanyDao companyDao;

    @Test
    public void testDaoEnvir() {
        List<Company> companyDaoAll = companyDao.findAll();
        companyDaoAll.stream().forEach(company -> System.out.println(company));
    }
}
