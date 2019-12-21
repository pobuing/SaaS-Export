package com.itheima.saas.dao.company;

import com.itheima.saas.domain.company.Company;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/16 18:28
 * @description: TODO
 * GOOD LUCK！
 */
public interface CompanyDao {
    List<Company> findAll();

    void save(Company company);

    Company findById(String id);

    void update(Company company);

    void deleteById(String id);

    /**
     * 查询总数
     *
     * @return
     */
    long findCount();

    /**
     * 分页查询
     *
     * @param index
     * @param size
     * @return
     */
    List<Company> findByPage(@Param("index") int index, @Param("size") int size);
}
