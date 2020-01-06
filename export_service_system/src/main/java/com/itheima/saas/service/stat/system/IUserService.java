package com.itheima.saas.service.stat.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.User;

/**
 * @author wangxin
 * @date 2019/12/21 20:54
 * @description: TODO
 * GOOD LUCK！
 */
public interface IUserService {
    //保存企业信息
    void save(User user);
    //通过id查询企业信息
    User findById(String id);
    //更新企业信息
    void update(User user);
    //通过id进行删除
    void delete(String id);
    //分页查询所有数据
    PageInfo<User> findAll(int page,int size,String companyId);

    User findByEmail(String email);

    User findByOpenId(String openId);
}
