package com.itheima.saas.service.system.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.system.SysLogDao;
import com.itheima.saas.domain.system.SysLog;
import com.itheima.saas.service.system.ISysLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangxin
 * @date 2019/12/23 16:35
 * @description: TODO
 * GOOD LUCK！
 */
@Service
public class ISysLogServiceImpl implements ISysLogService {

    @Autowired
    private SysLogDao sysLogDao;

    @Override
    public PageInfo<SysLog> findAll(String companyId, int page, int size) {
        //开启分页
        PageHelper.startPage(page, size);
        List<SysLog> sysLogList = sysLogDao.findAll(companyId);
        return new PageInfo<SysLog>(sysLogList);
    }

    @Override
    public void save(SysLog sysLog) {
        sysLogDao.save(sysLog);
    }
}
