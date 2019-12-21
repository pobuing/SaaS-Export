package com.itheima.saas.dao.system;

import com.itheima.saas.domain.system.User;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/21 20:32
 * @description: TODO
 * GOOD LUCK！
 */
public interface UserDao {
    //根据企业id查询全部
    List<User> findAll(String companyId);

    //根据id查询
    User findById(String id);

    //保存用户
    void save(User user);

    //删除用户
    void delete(String userId);

    //更新
    void update(User user);

}
