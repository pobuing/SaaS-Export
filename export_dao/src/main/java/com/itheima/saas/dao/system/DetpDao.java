package com.itheima.saas.dao.system;

import com.itheima.saas.domain.system.Dept;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/21 11:52
 * @description: TODO
 * GOOD LUCK！
 */
public interface DetpDao {
    //查询该企业下所有部门的信息
    List<Dept> findAll(String companyId);

    //根据id查询部门的信息
    Dept findById(String id);

    //保存部门dept的信息
    void save(Dept dept);

    //更新部门信息
    void update(Dept dept);

    //通过id删除部门信息
    void delete(String id);

    //查询总数
    long findCount();

    List<Dept> findByPage(@Param("companyId") String companyId,
                          @Param("index") int index,
                          @Param("size") int size);
}
