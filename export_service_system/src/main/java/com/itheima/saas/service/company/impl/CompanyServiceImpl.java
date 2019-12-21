package com.itheima.saas.service.company.impl;

import com.itheima.common.entity.PageBean;
import com.itheima.saas.dao.company.CompanyDao;
import com.itheima.saas.domain.company.Company;
import com.itheima.saas.service.company.ICompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public void save(Company company) {
        companyDao.save(company);
    }

    @Override
    public Company findById(String id) {
        return companyDao.findById(id);
    }

    @Override
    public void update(Company company) {
        companyDao.update(company);
    }

    @Override
    public void deleteById(String id) {
        companyDao.deleteById(id);
    }

    @Override
    public PageBean findByPage(int page, int size) {
        //查询总记录数
        long total = companyDao.findCount();
        List<Company> list = companyDao.findByPage((page - 1) * size, size);
        return new PageBean(total, list, page, size);
    }
}
