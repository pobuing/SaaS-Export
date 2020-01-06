package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.common.utils.MailUtil;
import com.itheima.saas.domain.cargo.ExportExample;
import com.itheima.saas.domain.cargo.Shipping;
import com.itheima.saas.domain.cargo.ShippingExample;
import com.itheima.saas.service.stat.cargo.ShippingService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

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
        // TODO: 发送邮件
        //发送邮件
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
       /* try {
            MailUtil.sendMsg(to, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        //将邮件内容写入到rabbitMQ中
        /**
         * 两个参数：1、routingKey：队列名称，对应于配置文件中的队列名
         *          2、传入的发送的消息参数
         */

        Map map = new HashMap();
        map.put("to", to);
        map.put("subject", subject);
        map.put("content", content);

        amqpTemplate.convertAndSend("mail.send", map);
        return "redirect:/cargo/shipping/list.do";
    }

}
