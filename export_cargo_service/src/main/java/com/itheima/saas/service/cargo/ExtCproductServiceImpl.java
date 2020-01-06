package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.ContractDao;
import com.itheima.saas.dao.cargo.ExtCproductDao;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ExtCproduct;
import com.itheima.saas.domain.cargo.ExtCproductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/30 19:42
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout=1200000)
public class ExtCproductServiceImpl implements ExtCproductService {

    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ContractDao contractDao;


    public PageInfo findAll(ExtCproductExample extCproductExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<ExtCproduct> list = extCproductDao.selectByExample(extCproductExample);
        return new PageInfo<ExtCproduct>(list);
    }

    public ExtCproduct findById(String id) {
        return extCproductDao.selectByPrimaryKey(id);
    }

    public void save(ExtCproduct extCproduct) {
        //1.传入的参数是附件实体类
        //2.通过传入参数计算附件的金额
        double amount = 0.0d;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //3.设置附件的金额
        extCproduct.setAmount(amount);
        //4.通过extCproduct.getContractId得到合同实体类
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        if (contract != null) {
            //5.计算合同的附件数量（合同本身的附件数量 + 本次附件的数量）
            //6.设置合同附件的数量
            if (contract.getExtNum() != null) {
                contract.setExtNum(contract.getExtNum() + extCproduct.getCnumber());
            } else {
                contract.setExtNum(extCproduct.getCnumber());
            }
            //7.计算合同的总金额（合同本身的总金额 + 本附件的金额）
            //8.设置合同的总金额
            if (contract.getTotalAmount() != null) {
                contract.setTotalAmount(contract.getTotalAmount() + amount);
            } else {
                contract.setTotalAmount(amount);
            }
            //9.写入附件数据库
            extCproductDao.insertSelective(extCproduct);
            //10.更新合同到数据库
            contractDao.updateByPrimaryKeySelective(contract);
        }
    }

    public void update(ExtCproduct extCproduct) {
        //1.传入的参数是附件实体类
        //2.通过传入参数计算附件的金额
        double amount = 0.0d;
        if (extCproduct.getCnumber() != null && extCproduct.getPrice() != null) {
            amount = extCproduct.getCnumber() * extCproduct.getPrice();
        }
        //3.设置附件的金额
        extCproduct.setAmount(amount);
        //4.通过extCproduct.getContractId得到合同实体类
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //5.通过extCproduct.getId得到原来的附件实体类
        ExtCproduct oldExtC = extCproductDao.selectByPrimaryKey(extCproduct.getId());
        //6.计算合同的附件数量（合同本身的附件数量 - 原来的附件数量 + 本次附件的数量）
        //7.设置合同的附件数量
        contract.setExtNum(contract.getExtNum() - oldExtC.getCnumber() + extCproduct.getCnumber());
        //8.计算合同的总金额（合同本身的总金额 - 原来的附件金额 + 本次附件的金额）
        //9.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - oldExtC.getAmount() + amount);
        //10.更新附件到数据库
        extCproductDao.updateByPrimaryKeySelective(extCproduct);
        //11.更新合同到数据库
        contractDao.updateByPrimaryKeySelective(contract);
    }

    public void delete(String id) {
        //1.通过id查询到附件的实体类
        ExtCproduct extCproduct = extCproductDao.selectByPrimaryKey(id);
        //2.通过附件实体类查询合同实体类
        Contract contract = contractDao.selectByPrimaryKey(extCproduct.getContractId());
        //3.计算合同的附件数量（合同本身的附件数量 - 本次附件的数量）
        //4.设置合同附件数量
        contract.setExtNum(contract.getExtNum() - extCproduct.getCnumber());
        //5.计算合同的总金额（合同本身的总金额 - 本次附件的金额）
        //6.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());
        //7.更新合同到数据库
        contractDao.updateByPrimaryKeySelective(contract);
        //8.删除附件
        extCproductDao.deleteByPrimaryKey(id);
    }
}
