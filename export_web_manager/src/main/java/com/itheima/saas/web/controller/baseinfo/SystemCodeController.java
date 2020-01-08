package com.itheima.saas.web.controller.baseinfo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNoExample;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;
import com.itheima.saas.service.cargo.FactoryService;
import com.itheima.saas.service.systemcode.SCProductNoService;
import com.itheima.saas.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2020/1/6 23:29
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/baseinfo/systemcode")
public class SystemCodeController extends BaseController {
    @Reference
    private SCProductNoService scProductNoService;

    @Reference
    private FactoryService factoryService;

    @RequestMapping("/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        SCProductNoExample scProductNoExample = new SCProductNoExample();

        PageInfo info = scProductNoService.findAll(scProductNoExample, page, size);
        request.setAttribute("page", info);
        return "system/code/sc-list";
    }

    @RequestMapping("/toAdd")
    public String toAdd() {
        //3. 通过factoryService查询所有货物的厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //4. 将货物厂家信息放入request域当中
        request.setAttribute("factoryList", factoryList);
        //5. 将contractId放入到request域当中
        return "system/code/sc-add";
    }

    @RequestMapping("/add")
    public String add(SCProductNo scProductNo) {
        scProductNo.setProductnumId(UUID.randomUUID().toString());
        Factory factory = factoryService.findById(scProductNo.getFactoryId());
        scProductNo.setFactoryName(factory.getFactoryName());
        //存入数据库
        scProductNoService.save(scProductNo);
        return "redirect:/baseinfo/systemcode/list.do";
    }


    @RequestMapping("/findProNoByfactoryId")
    @ResponseBody
    public String factoryId(String factoryId) {
        SCProductNoExample scProductNoExample = new SCProductNoExample();
        //设置条件
        SCProductNoExample.Criteria criteria = scProductNoExample.createCriteria();
        criteria.andFactoryIdEqualTo(factoryId);
        List<SCProductNo> list = scProductNoService.findProductsByFactoryId(scProductNoExample);
        String jsonString = JSONObject.toJSONString(list);
        System.out.println(jsonString);
        return jsonString;
    }
}
