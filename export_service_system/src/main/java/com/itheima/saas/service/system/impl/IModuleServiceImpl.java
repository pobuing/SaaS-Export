package com.itheima.saas.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.system.ModuleDao;
import com.itheima.saas.domain.system.Module;
import com.itheima.saas.service.system.IModuleService;
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
}
