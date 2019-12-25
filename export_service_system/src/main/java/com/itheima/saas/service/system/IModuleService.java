package com.itheima.saas.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:55
 * @description: TODO
 * GOOD LUCK！
 */
public interface IModuleService {
    //分页查询模块
    PageInfo<Module> findByPage(int page, int size);

    //根据id查询模块
    Module findById(String id);

    //新增模块
    void save(Module module);

    //删除模块
    void delete(String id);

    //修改模块
    void update(Module module);

    List<Module> findAll();

    List<Module> findByRoleId(String id);

    void updateRoleModule( String roleId,String moduleId);

    List<Module> findByUser(User user);
}
