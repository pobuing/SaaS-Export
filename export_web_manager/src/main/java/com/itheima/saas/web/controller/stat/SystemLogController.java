package com.itheima.saas.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.saas.service.stat.SystemLogService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/7 18:07
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/stat/systemlog")
public class SystemLogController extends BaseController {
    @Reference
    private SystemLogService systemLogService;

    @RequestMapping("/toTopVisit")
    public String toTopList(){
        return "system/visitcount/visitcount-sell";
    }

    @RequestMapping("/selectTopVisit")
    @ResponseBody
    public List topList() {
        List<Map<String, String>> list = systemLogService.findIpCount();

        return list;
    }
}

