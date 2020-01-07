package com.itheima.saas.web.controller.report;

import com.itheima.saas.domain.system.User;
import com.itheima.saas.service.stat.system.IUserService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/7 22:37
 * @description: TODO
 * GOOD LUCK！
 */
@RequestMapping("/report")
@Controller
public class AdviceController extends BaseController {
    public static final String MANAGER_ID = "9fe270f8-f682-4126-92aa-604d945c95f1";
    @Autowired
    private IUserService userService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    @RequestMapping("/toReport")
    public String toReport() {
        return "report/report";
    }

    @RequestMapping("/sendReport")
    @ResponseBody
    public String sendReport(String report) {
        if (!StringUtils.isEmpty(report)) {
            //查询管理员账号
            User manager = userService.findById(MANAGER_ID);
            User loginUser = (User) session.getAttribute("loginUser");
            // TODO: 发送邮件
            String to = manager.getEmail();
            String subject = "SaaS-Export意见问题反馈";
            String userinfo = "用户信息" +
                    "用户名：" + loginUser.getUserName() +
                    "\n\r所属公司" + loginUser.getCompanyName() +
                    "\n\r用户邮件：" + loginUser.getEmail();


            //将邮件内容写入到rabbitMQ中


            Map<String, String> map = new HashMap<String, String>();
            map.put("to", to);
            map.put("subject", subject);
            map.put("content", userinfo+"\n\r反馈内容："+report);
            amqpTemplate.convertAndSend("mail.send", map);
        }
        return "";
    }
}
