package com.itheima.saas.service.cargo;

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
     * @param factoryExample
     * @return
     */
    List<Factory> findAll(FactoryExample factoryExample);
}
