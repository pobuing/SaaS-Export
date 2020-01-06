package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.*;
import com.itheima.saas.domain.cargo.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2020/1/5 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceDao invoiceDao;

    @Autowired
    private PackingDao packingDao;

    @Autowired
    private ShippingDao shippingDao;
    @Autowired
    private ExportDao exportDao;
    @Autowired
    private ContractDao contractDao;

    @Override
    public PageInfo findAll(InvoiceExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Invoice> list = invoiceDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public Invoice findById(String id) {
        return invoiceDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Invoice Invoice) {
        invoiceDao.insertSelective(Invoice);
    }

    @Override
    public void update(Invoice Invoice) {
        invoiceDao.updateByPrimaryKeySelective(Invoice);
    }

    @Override
    public void delete(String id) {
        invoiceDao.deleteByPrimaryKey(id);
    }


}
