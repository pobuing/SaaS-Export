package com.itheima.saas.service.systemcode;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNoExample;

/**
 * @author wangxin
 * @date 2020/1/3 15:25
 * @description: TODO
 * GOOD LUCK！
 */
public interface SCProductNoService {
    /**
     * 查询所有
     *
     * @param scProductNoExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(SCProductNoExample scProductNoExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    SCProductNo findById(String id);

    /**
     * 保存合同信息
     *
     * @param shipping
     */
    void save(SCProductNo shipping);

    /**
     * 更新合同信息
     *
     * @param shipping
     */
    void update(SCProductNo shipping);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);
}