package com.itheima.saas.service.stat.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ContractExample;
import com.itheima.saas.domain.vo.ContractProductVo;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/29 21:17
 * @description: TODO
 * GOOD LUCK！
 */
public interface ContractService {
    /**
     * 查询所有
     *
     * @param contractExample
     * @param page
     * @param size
     * @return
     */
    PageInfo findAll(ContractExample contractExample, int page, int size);

    /**
     * 通过id查询合同实体
     *
     * @param id 合同id
     * @return
     */
    Contract findById(String id);

    /**
     * 保存合同信息
     *
     * @param contract
     */
    void save(Contract contract);

    /**
     * 更新合同信息
     *
     * @param contract
     */
    void update(Contract contract);

    /**
     * 根据id删除
     *
     * @param id
     */
    void delete(String id);

    List<ContractProductVo> findByInputDate(String inputDate, String companyId);

}
