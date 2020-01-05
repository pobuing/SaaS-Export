package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.service.stat.cargo.ExportService;
import com.itheima.saas.service.stat.cargo.PackingService;
import com.itheima.saas.service.stat.cargo.ShippingService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/5 19:45
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/cargo/packing")
public class PackingController extends BaseController {


    @Reference
    private PackingService packingService;

    @Reference
    private ExportService exportService;

    @Reference
    private ShippingService shippingService;

    /**
     * 显示已经报运后的报运单
     */
    @RequestMapping("/exportlist")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        ExportExample exportExample = new ExportExample();
        ExportExample.Criteria criteria = exportExample.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        criteria.andStateEqualTo(Long.valueOf(2));
        PageInfo pageInfo = exportService.findAll(exportExample, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/packing/packing-export-list";
    }

    @RequestMapping("/list")
    public String packingList(@RequestParam(defaultValue = "1") int page,
                              @RequestParam(defaultValue = "5") int size) {
        PackingExample example = new PackingExample();
        PackingExample.Criteria criteria = example.createCriteria();
        criteria.andCompanyIdEqualTo(companyId);
        PageInfo pageInfo = packingService.findAll(example, page, size);
        request.setAttribute("page", pageInfo);
        return "cargo/packing/packing-list";
    }

    /**
     * 提交装箱
     *
     * @param id
     * @return
     */
    @RequestMapping("/createBox")
    public String createBox(String id) {
        //按照,拆分ids
        Packing packing = new Packing();
        packing.setExportIds(id);
        request.setAttribute("packing", packing);
        return "cargo/packing/packing-add";
    }

    /**
     * 新建装箱
     *
     * @param packing
     * @return
     */
    @RequestMapping("/edit")
    public String edit(Packing packing) {
        packing.setPackingListId(UUID.randomUUID().toString());
        packing.setCompanyId(companyId);
        packing.setCompanyName(companyName);
        //设置状态已装箱
        packing.setState(1L);
        packingService.save(packing);
        return "redirect:/cargo/packing/list.do";
    }

    /**
     * 委托运输
     *
     * @param id
     * @return
     */
    @RequestMapping("/forwardOder")
    public String forwardOder(String id) {
        //创建委托单
        Shipping shipping = new Shipping();
        shipping.setShippingId(UUID.randomUUID().toString());
        //设置委托箱子号
        shipping.setPackingIds(id);
        shipping.setState(0L);
        //创建委托单
        shippingService.save(shipping);
        //改变箱子状态
        String[] split = id.split(",");
        for (String packingId : split) {
            Packing packing = packingService.findById(id);
            //设置箱子为已委托
            packing.setState(2l);
            packingService.update(packing);
        }
        return "redirect:/cargo/packing/list.do";
    }
}
