package com.itheima.saas.listener.mail;

import com.alibaba.fastjson.JSONObject;
import com.itheima.saas.common.utils.MailUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/5 16:37
 * @description: TODO
 * GOOD LUCK！
 */
public class MyListener implements MessageListener {

    public void onMessage(Message message) {
        System.out.println(message.getBody());

        String msg = new String(message.getBody());
        Map map = JSONObject.parseObject(msg, Map.class);

        System.out.println(map);

        String to = String.valueOf(map.get("to"));
        String subject = String.valueOf(map.get("subject"));
        String content = String.valueOf(map.get("content"));

        System.out.println(to+"||"+subject+"||"+content);

        try {
            MailUtil.sendMsg(to, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}