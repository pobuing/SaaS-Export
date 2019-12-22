package com.itheima.saas.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.system.RoleDao;
import com.itheima.saas.domain.system.Role;
import com.itheima.saas.service.system.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/22 00:35
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class IRoleServiceImpl implements IRoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public PageInfo<Role> findByPage(int page, int size, String companyId) {
        PageHelper.startPage(page, size);
        List<Role> roleList = roleDao.findAll(companyId);
        return new PageInfo<Role>(roleList);
    }

    @Override
    public Role findById(String id) {
        return roleDao.findById(id);
    }

    @Override
    public void save(Role role) {
        roleDao.save(role);
    }

    @Override
    public void update(Role role) {
        roleDao.update(role);
    }

    @Override
    public void delete(String id) {
        roleDao.delete(id);
    }

    @Override
    public List<Role> findByUserId(String id) {
        return roleDao.fineByUserId(id);
    }

    @Override
    public List<Role> findAll(String companyId) {
        return roleDao.findAll(companyId);
    }

    @Override
    public void changeRole(String userid, String roleIds) {
        //通过userId先删除这个用户的所有的角色信息
        roleDao.deleteByUserId(userid);
        //再插入新的角色信息
        String[] stringArr = roleIds.split(",");
        for (String roleId : stringArr) {
            roleDao.insertUserRole(userid, roleId);
        }
    }
}
