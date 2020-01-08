package com.itheima.test;

import com.itheima.saas.common.utils.Encrypt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author wangxin
 * @date 2020/1/8 17:33
 * @description: TODO
 * GOOD LUCK！
 */
public class UserTest {
    public static void main(String[] args) {
        String md5 = Encrypt.md5("123456", "wangxin2006@qq.com");
        System.out.println(md5);

    }

}
