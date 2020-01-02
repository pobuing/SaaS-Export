package com.itheima.saas.test;

import com.itheima.saas.dao.cargo.FactoryDao;
import com.itheima.saas.domain.cargo.Factory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/29 18:08
 * @description: TODO
 * GOOD LUCK！
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/spring/applicationContext-*.xml")
public class FactoryDaoTest {
    @Autowired
    private FactoryDao factoryDao;

    @Test
    public void insertTest() {
        Factory factory = new Factory();
        factory.setId(UUID.randomUUID().toString());
        factory.setCreateTime(new Date());
        factory.setUpdateTime(new Date());
        factory.setFactoryName("新明会所1");
        //一般使用的都是insertSelective
        factoryDao.insertSelective(factory);
    }
}
