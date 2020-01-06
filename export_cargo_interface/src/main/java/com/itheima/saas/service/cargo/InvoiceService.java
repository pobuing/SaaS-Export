package com.itheima.saas.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Invoice;
import com.itheima.saas.domain.cargo.InvoiceExample;

/**
 * @author wangxin
 * @date 2020/1/5 19:48
 * @description: TODO
 * GOOD LUCK！
 */
public interface InvoiceService {
    /**
     * 查询所有
     *
     * @param invoiceExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(InvoiceExample invoiceExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    Invoice findById(String id);

    /**
     * 保存合同信息
     *
     * @param invoice
     */
    void save(Invoice invoice);

    /**
     * 更新合同信息
     *
     * @param invoice
     */
    void update(Invoice invoice);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);


}

