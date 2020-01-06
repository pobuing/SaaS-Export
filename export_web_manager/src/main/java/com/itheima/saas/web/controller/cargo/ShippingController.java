package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.service.cargo.*;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/6 03:33
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/cargo/shipping")
public class ShippingController extends BaseController {
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Reference
    private ShippingService shippingService;
    @Reference
    private PackingService packingService;
    @Reference
    private ExportService exportService;
    @Reference
    private ContractService contractService;
    @Reference
    private InvoiceService invoiceService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        ShippingExample shippingExample = new ShippingExample();
        ShippingExample.Criteria criteria = shippingExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
//        criteria.andStateEqualTo(Long.valueOf(2));
        PageInfo pageInfo = shippingService.findAll(shippingExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/shipping/shipping-list";
    }

    @RequestMapping("/submit")
    public String submit(String id) {
        //根据id查询委托单
        Shipping shipping = shippingService.findById(id);
        request.setAttribute("shipping", shipping);
        return "cargo/shipping/shipping-add";

    }

    @RequestMapping("/edit")
    public String edit(Shipping shipping) {
        //给shipping设置公司id和name
        shipping.setCompanyId(companyId);
        shipping.setCompanyName(companyName);
        //改变状态
        shipping.setState(1L);
        //保存数据
        shippingService.update(shipping);
        //存储发票数据
        Invoice invoice = new Invoice();
        //创建单号
        invoice.setInvoiceId(UUID.randomUUID().toString());
        invoice.setShippingId(shipping.getShippingId());
        invoice.setTranscompanyName(shipping.getTranscompanyName());
        invoice.setCompanyId(companyId);
        invoice.setCompanyName(companyName);
        //结算
        accounting(invoice);
        //插入数据库
        invoiceService.save(invoice);
        // TODO: 发送邮件
        String to = shipping.getReceiverEmail();
        String subject = "SaaS-Export委托单";
        String content = "您使用的SaaS-Export平台通过：" +
                "委托单号：" + shipping.getShippingId() +
                "报运单号" + shipping.getPackingIds() +
                "出发地：" + shipping.getFromPort() +
                "目的地：" + shipping.getToPort() +
                "创建人：" + shipping.getCreateBy() +
                "创建时间：" + shipping.getCreateTime();
        System.out.println("邮件内容||" + to + "||" + subject + "||" + content);


        //将邮件内容写入到rabbitMQ中


        Map<String, String> map = new HashMap<String, String>();
        map.put("to", to);
        map.put("subject", subject);
        map.put("content", content);

        amqpTemplate.convertAndSend("mail.send", map);
        return "redirect:/cargo/shipping/list.do";
    }

    /**
     * 结算--发票
     *
     * @param invoice
     */
    public void accounting(Invoice invoice) {
        System.out.println("*****" + invoice);

        Double totalPrice = 0.0d;
        //取出委托单
        String shippingId = invoice.getShippingId();
        //根据委托单查询装箱数据
        Shipping shipping = shippingService.findById(shippingId);
        //获取装箱id
        String packingIds = shipping.getPackingIds();
        Packing packing = packingService.findById(packingIds);
        //获取箱子中的报运单
        String exportIds = packing.getExportIds();
        String[] exportArr = exportIds.split(",");
        //遍历报运单获取每个报运单中的合同
        for (String exportId : exportArr) {
            //得到报运单
            Export export = exportService.findById(exportId);
            //获取报运单的id
            String contractIds = export.getContractIds();
            //拆分id
            String[] contractArr = contractIds.split(",");
            for (String contractId : contractArr) {
                Contract contract = contractService.findById(contractId);
                //获取合同价格
                Double totalAmount = contract.getTotalAmount();
                totalPrice += totalAmount;
            }
        }
        //设置发票金额
        invoice.setTotalPrices(String.valueOf(totalPrice));
    }
}
