package com.itheima.saas.service.company;

import com.itheima.saas.common.entity.PageBean;
import com.itheima.saas.domain.company.Company;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/16 19:51
 * @description: TODO
 * GOOD LUCK！
 * company Service
 */
public interface ICompanyService {
    /**
     * 查询企业列表
     *
     * @return
     */
    List<Company> findAll();

    void save(Company company);

    Company findById(String id);

    void update(Company company);

    void deleteById(String id);

    PageBean findByPage(int page, int size);
}
