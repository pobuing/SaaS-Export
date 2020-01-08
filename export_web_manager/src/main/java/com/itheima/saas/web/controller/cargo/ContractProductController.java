package com.itheima.saas.web.controller.cargo;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.common.utils.UploadUtil;
import com.itheima.saas.domain.cargo.ContractProduct;
import com.itheima.saas.domain.cargo.ContractProductExample;
import com.itheima.saas.domain.cargo.Factory;
import com.itheima.saas.domain.cargo.FactoryExample;
import com.itheima.saas.service.cargo.ContractProductService;
import com.itheima.saas.service.cargo.FactoryService;
import com.itheima.saas.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * @author wangxin
 * @date 2019/12/29 22:24
 * @description: TODO
 * GOOD LUCK！
 */
@Controller
@RequestMapping(value = "/cargo/contractProduct")
public class ContractProductController extends BaseController {
    @Reference
    private ContractProductService contractProductService;
    @Reference
    private FactoryService factoryService;

    @RequestMapping(value = "/list")
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "5") int size,
                       String contractId) {
        //1. 通过contractProductService分页查询所有的货物信息
        ContractProductExample example = new ContractProductExample();
        ContractProductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdEqualTo(contractId);
        PageInfo pageInfo = contractProductService.findAll(example, page, size);

        System.out.println("======================" + pageInfo.getList().toString());
        //2. 将货物信息放入request域当中
        request.setAttribute("page", pageInfo);

        //3. 通过factoryService查询所有货物的厂家
        FactoryExample factoryExample = new FactoryExample();
        FactoryExample.Criteria factoryCriteria = factoryExample.createCriteria();
        factoryCriteria.andCtypeEqualTo("货物");
        List<Factory> factoryList = factoryService.findAll(factoryExample);
        //4. 将货物厂家信息放入request域当中
        request.setAttribute("factoryList", factoryList);

        //5. 将contractId放入到request域当中
        request.setAttribute("contractId", contractId);
        //6. 跳转货物列表页面
        return "cargo/product/product-list";
    }


    @RequestMapping(value = "/edit")
    public String edit(ContractProduct contractProduct, MultipartFile productPhoto) {
        //设置货物的公司Id
        contractProduct.setCompanyId(companyId);
        contractProduct.setCompanyName(companyName);
        if (productPhoto != null) {
            try {
                String upload = new UploadUtil().upload(productPhoto.getBytes());
                contractProduct.setProductImage(upload);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //设置工厂名称
        Factory factory = factoryService.findById(contractProduct.getFactoryId());
        contractProduct.setFactoryName(factory.getFactoryName());
        if (StringUtils.isEmpty(contractProduct.getId())) {
            contractProduct.setId(UUID.randomUUID().toString());
            //保存数据
            contractProductService.save(contractProduct);
        } else {
            contractProductService.update(contractProduct);
        }
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractProduct.getContractId();
    }


    @RequestMapping(value = "/toUpdate")
    public String toUpdate(String id) {
        ContractProduct contractProduct = contractProductService.findById(id);
        request.setAttribute("contractProduct", contractProduct);

        return "cargo/product/product-update";
    }

    @RequestMapping(value = "/delete")
    public String delete(String id, String contractId) {
        contractProductService.delete(id);
        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }

    @RequestMapping(value = "/toImport")
    public String toImport(String contractId) {
        request.setAttribute("contractId", contractId);
        return "cargo/product/product-import";
    }


    @RequestMapping(value = "/import")
    public String importExcel(String contractId, MultipartFile file) {
        try {
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                ContractProduct contractProduct = new ContractProduct();
                /*contractProduct.setFactoryName((String) getCellValue(row.getCell(1)));
                contractProduct.setProductNo((String) getCellValue(row.getCell(2)));
                //row.getCell(3).setCellType(CellType.NUMERIC);
                //contractProduct.setCnumber((int) row.getCell(3).getNumericCellValue());
                contractProduct.setCnumber(((Double) getCellValue(row.getCell(3))).intValue());
                contractProduct.setPackingUnit((String) getCellValue(row.getCell(4)));
                contractProduct.setLoadingRate((String) getCellValue(row.getCell(5)));
                contractProduct.setBoxNum((Integer) getCellValue(row.getCell(6)));
                contractProduct.setPrice((Double) getCellValue(row.getCell(7)));
                contractProduct.setProductDesc((String) getCellValue(row.getCell(8)));
                contractProduct.setProductRequest((String) getCellValue(row.getCell(9)));*/

                contractProduct.setFactoryName(row.getCell(1).getStringCellValue());
                contractProduct.setProductNo(row.getCell(2).getStringCellValue());
                contractProduct.setCnumber((int) row.getCell(3).getNumericCellValue());
                contractProduct.setPackingUnit(row.getCell(4).getStringCellValue());
                contractProduct.setLoadingRate(row.getCell(5).getNumericCellValue() + "");
                contractProduct.setBoxNum((int) row.getCell(6).getNumericCellValue());
                contractProduct.setPrice(row.getCell(7).getNumericCellValue());
                contractProduct.setProductDesc(row.getCell(8).getStringCellValue());
                contractProduct.setProductRequest(row.getCell(9).getStringCellValue());

                contractProduct.setId(UUID.randomUUID().toString());
                contractProduct.setCompanyName(companyName);
                contractProduct.setCompanyId(companyId);
                contractProduct.setContractId(contractId);
                //6.调用contractProductService.save方法写入数据库
                contractProductService.save(contractProduct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/cargo/contractProduct/list.do?contractId=" + contractId;
    }


    private Object getCellValue(Cell cell) {
        Object o = new Object();

        switch (cell.getCellType()) {
            case STRING:
                o = cell.getStringCellValue();
                break;
            case NUMERIC:
                o = cell.getNumericCellValue();
                break;
            case BOOLEAN:
                o = cell.getBooleanCellValue();
                break;
        }

        return o;
    }
}
