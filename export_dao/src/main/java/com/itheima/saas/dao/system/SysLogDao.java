package com.itheima.saas.dao.system;

import com.itheima.saas.domain.system.SysLog;

import java.util.List;

public interface SysLogDao {

    //查询全部
    List<SysLog> findAll(String companyId);

    //添加
    int save(SysLog log);
}