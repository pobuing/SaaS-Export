package com.itheima.saas.service.stat;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.saas.dao.systemlog.SystemLogDao;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/7 17:25
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class SystemLogServiceImpl implements SystemLogService {

    @Autowired
    private SystemLogDao systemLogDao;

    public List<Map<String, String>> findIpCount() {
        return systemLogDao.selectIpCount();
    }
}
