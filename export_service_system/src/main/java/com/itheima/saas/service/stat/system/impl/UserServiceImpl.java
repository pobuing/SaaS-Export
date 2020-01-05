package com.itheima.saas.service.stat.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.system.UserDao;
import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/21 20:57
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class UserServiceImpl implements IUserService {
    //注入dao
    @Autowired
    private UserDao userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void delete(String id) {
        userDao.delete(id);
    }

    @Override
    public PageInfo findAll(int page, int size, String companyId) {
        PageHelper.startPage(page, size);
        List<User> list = userDao.findAll(companyId);
        //构造PageInfo
        return new PageInfo(list);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }
}
