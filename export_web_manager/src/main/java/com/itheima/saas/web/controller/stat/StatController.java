package com.itheima.saas.web.controller.stat;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.saas.service.stat.StatService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/3 15:55
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/stat")
public class StatController extends BaseController {
    @Reference
    private StatService statService;
    @RequestMapping(value = "/demo")
    public String demo(){
        return "stat/stat-demo";
    }

    //chartsType=factory
    @RequestMapping(value = "/toCharts")
    public String toCharts(String chartsType){
        return "stat/stat-"+chartsType;
    }


    @RequestMapping(value = "/getFactoryData")
    @ResponseBody
    public List<Map> getFactoryData(){
        //获取数据
        return statService.getFactoryData(companyId);
    }


    @RequestMapping(value = "/getOnlineData")
    @ResponseBody
    public List<Map> getOnlineData(){
        return statService.getOnlineData(companyId);
    }

    @RequestMapping(value = "/getSellData")
    @ResponseBody
    public List<Map> getSellData(){
        return statService.getSellData(companyId);
    }
}
