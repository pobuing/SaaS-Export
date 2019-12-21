package com.itheima.saas.service.system;

import com.itheima.common.entity.PageBean;
import com.itheima.saas.domain.system.Dept;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/21 11:58
 * @description: TODO
 * GOOD LUCK！
 */
public interface IDeptService {
    //分页查询该企业下的所有部门
    PageBean findByPage(int page, int size, String companyId);

    List<Dept> findAll(String companyId);

    //根据id查询部门信息
    Dept findById(String id);

    //保存部门信息
    void save(Dept dept);

    //更新部门信息
    void update(Dept dept);

    //通过id删除部门信息
    void delete(String id);

}
