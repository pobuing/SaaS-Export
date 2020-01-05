package com.itheima.saas.service.stat.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.ContractDao;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ContractExample;
import com.itheima.saas.domain.vo.ContractProductVo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/29 21:23
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout = 1200000)
public class ContractServiceImpl implements ContractService {
    @Autowired
    private ContractDao contractDao;

    public PageInfo findAll(ContractExample contractExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<Contract> list = contractDao.selectByExample(contractExample);
        return new PageInfo<Contract>(list);
    }

    public Contract findById(String id) {
        return contractDao.selectByPrimaryKey(id);
    }

    public void save(Contract contract) {
        contractDao.insert(contract);
    }

    public void update(Contract contract) {
        contractDao.updateByPrimaryKeySelective(contract);
    }

    public void delete(String id) {
        contractDao.deleteByPrimaryKey(id);
    }

    public List<ContractProductVo> findByInputDate(String inputDate, String companyId) {

        return contractDao.findByInputDate(inputDate, companyId);
    }
}
