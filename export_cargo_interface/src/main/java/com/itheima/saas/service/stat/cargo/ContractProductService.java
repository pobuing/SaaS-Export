package com.itheima.saas.service.stat.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.ContractProduct;
import com.itheima.saas.domain.cargo.ContractProductExample;

/**
 * @author wangxin
 * @date 2019/12/29 22:15
 * @description: TODO
 * GOOD LUCK！
 */
public interface ContractProductService {
    /**
     * 查询所有货物列表
     *
     * @param example
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(ContractProductExample example, int page, int size);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    ContractProduct findById(String id);

    /**
     * 保存数据
     *
     * @param contractProduct
     */
    void save(ContractProduct contractProduct);

    /**
     * 更新合同货物数据
     *
     * @param contractProduct
     */
    void update(ContractProduct contractProduct);

    /**
     * 通过id删除合同
     *
     * @param id
     */
    void delete(String id);
}
