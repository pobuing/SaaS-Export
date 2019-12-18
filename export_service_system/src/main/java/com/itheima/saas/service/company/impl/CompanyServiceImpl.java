package com.itheima.saas.service.company.impl;

import com.itheima.saas.dao.company.CompanyDao;
import com.itheima.saas.domain.company.Company;
import com.itheima.saas.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/16 19:52
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class CompanyServiceImpl implements ICompanyService {
    //注入dao
    @Autowired(required = false)
    private CompanyDao companyDao;

    public List<Company> findAll() {
        return companyDao.findAll();
    }
}
