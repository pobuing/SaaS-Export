package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.service.cargo.FinanceService;
import com.itheima.saas.service.cargo.InvoiceService;
import com.itheima.saas.service.cargo.PackingService;
import com.itheima.saas.service.cargo.ShippingService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/6 10:58
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/cargo/invoice")
public class InvoiceController extends BaseController {
    @Reference
    private InvoiceService invoiceService;

    @Reference
    private ShippingService shippingService;

    @Reference
    private PackingService packingService;
    @Reference
    private FinanceService financeService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        InvoiceExample invoiceExample = new InvoiceExample();
        InvoiceExample.Criteria criteria = invoiceExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = invoiceService.findAll(invoiceExample,
                page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/invoice/invoice-list";
    }

    @RequestMapping("/submit")
    public String submit(String id) {
        Invoice invoice = invoiceService.findById(id);
        request.setAttribute("invoice", invoice);
        return "cargo/invoice/invoice-submit";
    }

    @RequestMapping("/edit")
    public String edit(Invoice invoice) {
        invoice.setState(1L);
        Integer num = 0;
        invoiceService.update(invoice);
        //创建财务
        Finance finance = new Finance();
        //写入财务管理
        String shippingId = invoice.getShippingId();
        //查询委托单
        Shipping shipping = shippingService.findById(shippingId);
        //通过委托单获得装箱的id
        String packingIds = shipping.getPackingIds();
        //通过箱子查询箱子中的合同个数
        Packing packing = packingService.findById(packingIds);
        String exportIds = packing.getExportIds();
        num = exportIds.split(",").length;
        finance.setNum(String.valueOf(num));
        finance.setPrice(invoice.getTotalPrices());
        finance.setCompanyId(companyId);
        finance.setCompanyName(companyName);
        finance.setConsumerName(shipping.getReceiverName());
        finance.setExportNo(exportIds);
        finance.setContractNo(shipping.getShippingId());
        finance.setFinanceId(UUID.randomUUID().toString());
        financeService.save(finance);
        return "redirect:/cargo/invoice/list.do";
    }
}
