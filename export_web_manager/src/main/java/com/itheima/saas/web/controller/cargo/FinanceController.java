package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Finance;
import com.itheima.saas.domain.cargo.FinanceExample;
import com.itheima.saas.domain.cargo.Invoice;
import com.itheima.saas.domain.cargo.InvoiceExample;
import com.itheima.saas.service.cargo.FinanceService;
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
@RequestMapping("/cargo/finance")
public class FinanceController extends BaseController {
    @Reference
    private FinanceService financeService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        FinanceExample financeExample = new FinanceExample();
        FinanceExample.Criteria criteria = financeExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = financeService.findAll(financeExample,
                page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/finance/finance-list";
    }
}
