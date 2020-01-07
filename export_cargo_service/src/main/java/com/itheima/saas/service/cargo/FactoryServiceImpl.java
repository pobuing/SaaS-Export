package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.FactoryDao;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/29 22:23
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout = 1200000)
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;

    @Override
    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }

    @Override
    public Factory findById(String id) {
        return factoryDao.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo findByPage(FactoryExample factoryExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<Factory> list = factoryDao.selectByExample(factoryExample);
        return new PageInfo<Factory>(list);
    }

    @Override
    public void save(Factory factory) {
        factoryDao.insertSelective(factory);
    }

    @Override
    public void update(Factory factory) {
        factoryDao.updateByPrimaryKeySelective(factory);
    }

    @Override
    public void delete(String id) {
        factoryDao.deleteByPrimaryKey(id);
    }
}
