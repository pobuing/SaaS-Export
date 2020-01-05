package com.itheima.saas.service.stat.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.Role;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:33
 * @description: TODO
 * GOOD LUCK！
 */
public interface IRoleService {
    //分页查询角色列表
    PageInfo<Role> findByPage(int page, int size, String companyId);

    //根据id查询角色
    Role findById(String id);

    //新增角色
    void save(Role role);

    //修改角色
    void update(Role role);

    //删除角色
    void delete(String id);

    List<Role> findByUserId(String id);

    //查询所有角色列表，不分页
    List<Role> findAll(String companyId);

    void changeRole(String userid, String roleIds);
}
