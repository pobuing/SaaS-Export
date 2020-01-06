package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Invoice;
import com.itheima.saas.domain.cargo.InvoiceExample;
import com.itheima.saas.service.cargo.InvoiceService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        invoiceService.update(invoice);
        //写入财务管理
        return "redirect:/cargo/invoice/list.do";
    }
}
