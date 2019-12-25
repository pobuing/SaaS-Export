package com.itheima.saas.service.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.SysLog;

/**
 * @author wangxin
 * @date 2019/12/23 16:33
 * @description: TODO
 * GOOD LUCK！
 * 系统日志Service
 */
public interface ISysLogService {
    //翻页查询syslog日志
    PageInfo<SysLog> findAll(String companyId, int page, int size);

    //保存日志
    void save(SysLog sysLog);
}
