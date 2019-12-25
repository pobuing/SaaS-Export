package com.itheima.saas.dao.system;

import com.itheima.saas.domain.system.Module;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:50
 * @description: TODO
 * GOOD LUCK！
 */
public interface ModuleDao {
    //查询所有模块
    List<Module> findAll();

    //根据id返回模块
    Module findById(String id);

    //保存模块
    void save(Module module);

    //删除模块
    void delete(String id);

    //修改模块
    void update(Module module);

    //根据roleid查询该角色具有的module列表
    List<Module> findByRoleId(String id);

    void deleteByRoleId(String roleid);

    void insertRoleModule(@Param("roleId") String roleId, @Param("moduleId") String moduleId);

    List<Module> findByBelong(int belong);

    List<Module> findByUserId(String id);
}
