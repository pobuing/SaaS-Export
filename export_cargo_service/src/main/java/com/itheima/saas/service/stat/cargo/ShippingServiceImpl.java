package com.itheima.saas.service.stat.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.PackingDao;
import com.itheima.saas.dao.cargo.ShippingDao;
import com.itheima.saas.domain.cargo.Packing;
import com.itheima.saas.domain.cargo.PackingExample;
import com.itheima.saas.domain.cargo.Shipping;
import com.itheima.saas.domain.cargo.ShippingExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2020/1/5 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class ShippingServiceImpl implements ShippingService {

    @Autowired
    private ShippingDao shippingDao;

    @Override
    public PageInfo findAll(ShippingExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Shipping> list = shippingDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public Shipping findById(String id) {
        return shippingDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Shipping shipping) {
        shippingDao.insertSelective(shipping);
    }

    @Override
    public void update(Shipping shipping) {
        shippingDao.updateByPrimaryKeySelective(shipping);
    }

    @Override
    public void delete(String id) {
        shippingDao.deleteByPrimaryKey(id);
    }
}
