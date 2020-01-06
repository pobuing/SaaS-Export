package com.itheima.saas.test;

import com.itheima.saas.dao.cargo.*;
import com.itheima.saas.dao.company.CompanyDao;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.domain.company.Company;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/16 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/spring/applicationContext-*.xml")
public class TestEnvironment {
    @Autowired
    private CompanyDao companyDao;
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
    @Test
    public void testDaoEnvir() {
        List<Company> companyDaoAll = companyDao.findAll();
        companyDaoAll.stream().forEach(company -> System.out.println(company));
    }


    @Test
    public void testInvoice(){
        Invoice invoice = new Invoice();
        //创建单号
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setShippingId("14f879e7-b71d-4c1c-96ee-9c33ab07fe56");
        System.out.println("*****"+invoice);

            Double totalPrice = 0.0d;
            //取出委托单
            String shippingId = invoice.getShippingId();
            //根据委托单查询装箱数据
            Shipping shipping = shippingDao.selectByPrimaryKey(shippingId);
            //获取装箱id
            String packingIds = shipping.getPackingIds();
            Packing packing = packingDao.selectByPrimaryKey(packingIds);
            //获取箱子中的报运单
            String exportIds = packing.getExportIds();
            String[] exportArr = exportIds.split(",");
            //遍历报运单获取每个报运单中的合同
            for (String exportId : exportArr) {
                //得到报运单
                Export export = exportDao.selectByPrimaryKey(exportId);
                //获取报运单的id
                String contractIds = export.getContractIds();
                //拆分id
                String[] contractArr = contractIds.split(",");
                for (String contractId : contractArr) {
                    Contract contract = contractDao.selectByPrimaryKey(contractId);
                    //获取合同价格
                    Double totalAmount = contract.getTotalAmount();
                    totalPrice += totalAmount;
                }
            }
            //设置发票金额
            invoice.setTotalPrices(String.valueOf(totalPrice));
    }
}
