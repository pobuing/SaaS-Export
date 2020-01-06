package com.itheima.saas.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Finance;
import com.itheima.saas.domain.cargo.FinanceExample;

/**
 * @author wangxin
 * @date 2020/1/5 19:48
 * @description: TODO
 * GOOD LUCK！
 */
public interface FinanceService {
    /**
     * 查询所有
     *
     * @param financeExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(FinanceExample financeExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    Finance findById(String id);

    /**
     * 保存合同信息
     *
     * @param finance
     */
    void save(Finance finance);

    /**
     * 更新合同信息
     *
     * @param finance
     */
    void update(Finance finance);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);


}

