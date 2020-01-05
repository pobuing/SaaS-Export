package com.itheima.saas.web.controller.system;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.system.SysLog;
import com.itheima.saas.service.stat.system.ISysLogService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author wangxin
 * @date 2019/12/23 16:38
 * @description: TODO
 * GOOD LUCK！
 */
@RequestMapping(value = "/system/log")
@Controller
public class SysLogController extends BaseController {

    @Autowired
    private ISysLogService sysLogService;

    @RequestMapping(value = "/list", name = "日志列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        PageInfo<SysLog> pageInfo = sysLogService.findAll(companyId, page, size);
        //存放到request
        request.setAttribute("page", pageInfo);
        return "system/log/log-list";
    }
}
