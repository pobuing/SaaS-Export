package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.ContractDao;
import com.itheima.saas.dao.cargo.ContractProductDao;
import com.itheima.saas.domain.cargo.Contract;
import com.itheima.saas.domain.cargo.ContractProduct;
import com.itheima.saas.domain.cargo.ContractProductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/29 22:21
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout=1200000)
public class ContractProductServiceImpl implements ContractProductService {

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ContractDao contractDao;

    public PageInfo findAll(ContractProductExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<ContractProduct> list = contractProductDao.selectByExample(example);
        return new PageInfo<ContractProduct>(list);
    }

    public ContractProduct findById(String id) {
        return contractProductDao.selectByPrimaryKey(id);
    }

    public void save(ContractProduct contractProduct) {
        //1.得到的参数是contractProduct
        //2.通过contractProduct.cnumber * contractProduct.price得到货物的金额
        double amount = 0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //3.设置货物的金额
        contractProduct.setAmount(amount);
        //4.通过contractProduct.contractId得到合同的实体类
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        if (contract!=null){
            if (contract.getProNum()!=null){
                //5.计算合同主表上的货物数量，通过合同主表本身的货物数量+本次货物的数量
                //6.设置合同的货物数量
                contract.setProNum(contract.getProNum() + contractProduct.getCnumber());
            }else {
                //第一次写入合同
                contract.setProNum(contractProduct.getCnumber());
            }

            if (contract.getTotalAmount()!=null){
                //7.计算合同主表上的总金额，通过合同主表本身的总金额+本次货物的金额
                //8.设置合同的总金额
                contract.setTotalAmount(contract.getTotalAmount() + amount);
            }else {
                //第一次写入合同
                contract.setTotalAmount(amount);
            }
            //9.保存货物到数据库
            contractProductDao.insertSelective(contractProduct);
            //10.更新合同到数据库
            // update co_contract set pro_num = 12312, total_amount =  123 where id = 123
            contractDao.updateByPrimaryKeySelective(contract);
        }
    }

    public void update(ContractProduct contractProduct) {
        //1.传入的参数contractProduct
        //2.通过contractProduct.cnumber * contractProduct.price得到货物的金额
        double amount = 0.0d;
        if (contractProduct.getCnumber()!=null&&contractProduct.getPrice()!=null){
            amount = contractProduct.getCnumber() * contractProduct.getPrice();
        }
        //3.设置货物的金额
        contractProduct.setAmount(amount);
        //4.通过contractProduct.contractId得到合同的实体类
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        // 2-->3, 参数contractProduct-->3，contractProduct.id
        //5.通过contractProduct.id查询原来的货物实体类
        ContractProduct oldCP = contractProductDao.selectByPrimaryKey(contractProduct.getId());
        //6.计算合同的货物数量，通过合同本身货物数量 - 原来的货物数量 + 本次的货物数量
        //7.设置合同的货物数量
        contract.setProNum(contract.getProNum() - oldCP.getCnumber() + contractProduct.getCnumber());
        //8.计算合同的总金额，通过合同本身的总金额 - 原来的货物金额 + 本次的货物金额
        //9.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - oldCP.getAmount() + contractProduct.getAmount());
        //10.更新货物到数据库
        contractProductDao.updateByPrimaryKeySelective(contractProduct);
        //11.更新合同到数据库
        contractDao.updateByPrimaryKeySelective(contract);
    }

    public void delete(String id) {
        //1.查询货物的实体类
        ContractProduct contractProduct = contractProductDao.selectByPrimaryKey(id);
        //2.通过货物实体类里的合同id查询合同的实体类
        Contract contract = contractDao.selectByPrimaryKey(contractProduct.getContractId());
        //3.计算合同的货物数量，通过合同本身的货物数量 - 本次要删除的货物数量
        //4.设置合同的货物数量
        contract.setProNum(contract.getProNum() - contractProduct.getCnumber());
        //5.计算合同的总金额，通过合同本身的总金额 - 本次要删除的货物金额
        //6.设置合同的总金额
        contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount());
        //7.更新合同到数据库
        contractDao.updateByPrimaryKeySelective(contract);
        //8.通过id删除货物
        contractProductDao.deleteByPrimaryKey(id);
    }
}
