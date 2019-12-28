package com.itheima.saas.web.controller.company;

import com.itheima.saas.common.entity.PageBean;
import com.itheima.saas.domain.company.Company;
import com.itheima.saas.service.company.ICompanyService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/16 20:50
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping("/company")
public class CompanyController extends BaseController {
    @Autowired
    private ICompanyService companyService;


    @RequestMapping(value = "/list", name = "企业列表")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size) {
        PageBean pageBean = companyService.findByPage(page, size);
        request.setAttribute("page", pageBean);
        return "company/company-list";
    }

    @RequestMapping(value = "/toAdd", name = "跳转企业添加页面")
    public String toAdd() {
        return "company/company-add";
    }

    /**
     * 修改和删除同一个方法
     * 逻辑：
     *
     * @param company
     * @return
     */
    @RequestMapping(value = "/edit", name = "保存企业信息")
    public String edit(Company company) {

        if (StringUtils.isEmpty(company.getId())) {
            //为空表示新增
            company.setId(UUID.randomUUID().toString());
            companyService.save(company);
        } else {
            //不为空 编辑 修改
            companyService.update(company);
        }
        return "redirect:/company/list.do";
    }

    @RequestMapping(value = "/toUpdate", name = "跳转企业编辑页面")
    public String toUpdate(String id, HttpServletRequest request) {
        //通过id查询Company
        Company company = companyService.findById(id);
        //放进请求域中
        request.setAttribute("company", company);
        return "company/company-update";
    }


    @RequestMapping(value = "/delete", name = "删除信息")
    public String delete(String id) {
        companyService.deleteById(id);
        return "redirect:/company/list.do";
    }


}
