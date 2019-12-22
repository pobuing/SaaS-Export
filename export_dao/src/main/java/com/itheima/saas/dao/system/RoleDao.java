package com.itheima.saas.dao.system;

import com.itheima.saas.domain.system.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:25
 * @description: TODO
 * GOOD LUCK！
 */
public interface RoleDao {
    //分页查询角色列表
    List<Role> findAll(String companyId);

    //添加角色
    void save(Role role);

    //根据角色id查询角色
    Role findById(String id);

    //根据id删除角色
    void delete(String id);

    //修改角色
    void update(Role role);

    List<Role> fineByUserId(String id);

    void deleteByUserId(String userId);

    void insertUserRole(@Param("userId") String userId,
                        @Param("roleId") String roleId);
}
