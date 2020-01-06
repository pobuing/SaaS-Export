package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.*;
import com.itheima.saas.domain.cargo.Finance;
import com.itheima.saas.domain.cargo.FinanceExample;
import com.itheima.saas.domain.cargo.Invoice;
import com.itheima.saas.domain.cargo.InvoiceExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2020/1/5 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class FinanceServiceImpl implements FinanceService {

    @Autowired
    private FinanceDao financeDao;

    @Autowired
    private PackingDao packingDao;

    @Autowired
    private ShippingDao shippingDao;
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo findAll(FinanceExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Finance> list = financeDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public Finance findById(String id) {
        return financeDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Finance finance) {
        financeDao.insertSelective(finance);
    }

    @Override
    public void update(Finance finance) {
        financeDao.updateByPrimaryKeySelective(finance);
    }

    @Override
    public void delete(String id) {
        financeDao.deleteByPrimaryKey(id);
    }


}
