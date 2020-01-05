package com.itheima.saas.service.stat.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.system.ModuleDao;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:57
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class IModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleDao moduleDao;

    @Override
    public PageInfo<Module> findByPage(int page, int size) {
        PageHelper.startPage(page, size);
        List<Module> moduleList = moduleDao.findAll();
        return new PageInfo<>(moduleList);
    }

    @Override
    public Module findById(String id) {
        return moduleDao.findById(id);
    }

    @Override
    public void save(Module module) {
        moduleDao.save(module);
    }

    @Override
    public void delete(String id) {
        moduleDao.delete(id);
    }

    @Override
    public void update(Module module) {
        moduleDao.update(module);

    }

    @Override
    public List<Module> findAll() {
        return moduleDao.findAll();
    }

    @Override
    public List<Module> findByRoleId(String id) {
        return moduleDao.findByRoleId(id);
    }

    @Override
    public void updateRoleModule(String roleid, String moduleIds) {
        //根据roleId清空中间表
        moduleDao.deleteByRoleId(roleid);
        //循环moduleIds 插入新的数据
        String[] modules = moduleIds.split(",");
        for (String moduleId : modules) {
            moduleDao.insertRoleModule(roleid, moduleId);
        }
    }

    @Override
    public List<Module> findByUser(User user) {
        //1、如果User是SaaS平台管理员 user.degree==0，查找到的是SaaS管理这个模块，module.belong=0
        if (user.getDegree() == 0) {
            return moduleDao.findByBelong(0);
        } else if (user.getDegree() == 1) {
            return moduleDao.findByBelong(1);
        } else {
            //3、如果User是企业其他用户，根据User查找该用户对应的模块
            return moduleDao.findByUserId(user.getId());
        }
    }
}
