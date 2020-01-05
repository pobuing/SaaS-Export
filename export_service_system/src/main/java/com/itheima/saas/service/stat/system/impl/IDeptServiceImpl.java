package com.itheima.saas.service.stat.system.impl;

import com.itheima.saas.common.entity.PageBean;
import com.itheima.saas.dao.system.DetpDao;
import com.itheima.saas.domain.system.Dept;
import com.itheima.saas.service.stat.system.IDeptService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/21 12:18
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class IDeptServiceImpl implements IDeptService {
    //注入dao
    @Autowired
    private DetpDao detpDao;

    @Override
    public PageBean findByPage(int page, int size, String companyId) {
        //查询总数
        long count = detpDao.findCount();
        List<Dept> deptList = detpDao.findByPage(companyId, (page - 1) * size, size);
        return new PageBean(count, deptList, page, size);
    }


    @Override
    public List<Dept> findAll(String companyId) {
        List<Dept> list = detpDao.findAll(companyId);

        return list;
    }

    @Override
    public Dept findById(@Param("id") String id) {
        return detpDao.findById(id);
    }

    @Override
    public void save(Dept dept) {
        detpDao.save(dept);
    }

    @Override
    public void update(Dept dept) {
        detpDao.update(dept);
    }

    @Override
    public void delete(String id) {
        detpDao.delete(id);
    }
}
