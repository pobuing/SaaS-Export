package com.itheima.saas.service.systemcode;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.baseinfo.systemcode.SCProductNoDao;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNoExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author wangxin
 * @date 2020/1/6 23:25
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout = 1200000)
public class SCProductNoServiceImpl implements SCProductNoService {
    @Autowired
    private SCProductNoDao scProductNoDao;

    public PageInfo findAll(SCProductNoExample scProductNoExample, int page, int size) {
        PageHelper.startPage(page, size);
        List<SCProductNo> list = scProductNoDao.selectByExample(scProductNoExample);
        return new PageInfo(list);
    }

    public SCProductNo findById(String id) {
        return scProductNoDao.selectByPrimaryKey(id);
    }

    public void save(SCProductNo scProductNo) {
        scProductNoDao.insertSelective(scProductNo);
    }

    public void update(SCProductNo scProductNo) {
        scProductNoDao.updateByPrimaryKeySelective(scProductNo);
    }

    public void delete(String id) {
        scProductNoDao.deleteByPrimaryKey(id);
    }

    public List<SCProductNo> findProductsByFactoryId(SCProductNoExample example) {
        List<SCProductNo> productNoList = scProductNoDao.selectByExample(example);
        return productNoList;
    }
}
