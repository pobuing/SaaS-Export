package com.itheima.saas.service.stat.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ContractExample;
import com.itheima.saas.domain.cargo.Packing;
import com.itheima.saas.domain.cargo.PackingExample;

/**
 * @author wangxin
 * @date 2020/1/5 19:48
 * @description: TODO
 * GOOD LUCK！
 */
public interface PackingService {
    /**
     * 查询所有
     *
     * @param contractExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(PackingExample contractExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    Packing findById(String id);

    /**
     * 保存合同信息
     *
     * @param contract
     */
    void save(Packing contract);

    /**
     * 更新合同信息
     *
     * @param contract
     */
    void update(Packing contract);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);
}
