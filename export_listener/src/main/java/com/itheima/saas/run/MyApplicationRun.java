package com.itheima.saas.run;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyApplicationRun {

    public static void main(String[] args) {

        //通过ClassPathXmlApplicationContext运行之后，可以直接进行监听
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("spring/applicationContext-listener.xml");
    }
}
