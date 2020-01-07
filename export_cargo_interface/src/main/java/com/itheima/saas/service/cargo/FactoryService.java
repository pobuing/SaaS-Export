package com.itheima.saas.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.ExtCproductExample;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/29 22:18
 * @description: TODO
 * GOOD LUCK！
 */
public interface FactoryService {
    /**
     * 查询所有厂家信息
     *
     * @param factoryExample
     * @return
     */
    List<Factory> findAll(FactoryExample factoryExample);

    PageInfo findByPage(FactoryExample factoryExample, int page, int size);


    Factory findById(String id);

    void update(Factory factory);
    void save(Factory factory);

    void delete(String id);
}
