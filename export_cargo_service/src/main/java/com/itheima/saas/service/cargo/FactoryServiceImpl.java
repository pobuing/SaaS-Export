package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
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
@Service(timeout=1200000)
public class FactoryServiceImpl implements FactoryService {
    @Autowired
    private FactoryDao factoryDao;

    public List<Factory> findAll(FactoryExample factoryExample) {
        return factoryDao.selectByExample(factoryExample);
    }
}
