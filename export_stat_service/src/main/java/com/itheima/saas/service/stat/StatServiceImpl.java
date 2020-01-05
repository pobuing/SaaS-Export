package com.itheima.saas.service.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.saas.dao.stat.StatDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/3 15:46
 * @description: TODO
 * GOOD LUCK！
 */
@Service(timeout = 1200000)
public class StatServiceImpl implements StatService {
    @Autowired
    private StatDao statDao;

    public List<Map> getFactoryData(String companyId) {
        return statDao.getFactoryData(companyId);
    }

    public List<Map> getOnlineData(String companyId) {
        return statDao.getOnlineData(companyId);
    }

    public List<Map> getSellData(String companyId) {
        return statDao.getSellData(companyId);
    }
}
