package com.itheima.saas.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Packing;
import com.itheima.saas.domain.cargo.PackingExample;
import com.itheima.saas.domain.cargo.Shipping;
import com.itheima.saas.domain.cargo.ShippingExample;

/**
 * @author wangxin
 * @date 2020/1/5 19:48
 * @description: TODO
 * GOOD LUCK！
 */
public interface ShippingService {
    /**
     * 查询所有
     *
     * @param shippingExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(ShippingExample shippingExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    Shipping findById(String id);

    /**
     * 保存合同信息
     *
     * @param shipping
     */
    void save(Shipping shipping);

    /**
     * 更新合同信息
     *
     * @param shipping
     */
    void update(Shipping shipping);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);
}
