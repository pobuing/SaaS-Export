package com.itheima.saas.service.cargo;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.PackingDao;
import com.itheima.saas.domain.cargo.Packing;
import com.itheima.saas.domain.cargo.PackingExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2020/1/5 19:47
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class PackingServiceImpl implements PackingService {

    @Autowired
    private PackingDao packingDao;

    @Override
    public PageInfo findAll(PackingExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Packing> list = packingDao.selectByExample(example);
        return new PageInfo(list);
    }

    @Override
    public Packing findById(String id) {
        return packingDao.selectByPrimaryKey(id);
    }

    @Override
    public void save(Packing packing) {
        packingDao.insertSelective(packing);
    }

    @Override
    public void update(Packing packing) {
        packingDao.updateByPrimaryKeySelective(packing);
    }

    @Override
    public void delete(String id) {
        packingDao.deleteByPrimaryKey(id);
    }
}
