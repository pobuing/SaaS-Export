package com.itheima.saas.service.cargo;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.*;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.domain.vo.ExportProductResult;
import com.itheima.saas.domain.vo.ExportResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ExportServiceImpl implements ExportService {

    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao;

    @Autowired
    private ExportProductDao exportProductDao;


    @Autowired
    private ExtCproductDao extCproductDao;

    @Autowired
    private ExtEproductDao extEproductDao;

    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    public void save(Export export) {
        //1、export有contractIds，合同编号

        int proNum = 0;
        int extNum = 0;

        List<ExportProduct> epList = new ArrayList<ExportProduct>();

        //2、设置export的状态为0
        export.setState(0);
        //3、循环contractIds，得到合同contractId
        String[] contractIds = export.getContractIds().split(",");
        for (String contractId : contractIds) {
            //4、通过合同的id查询合同实体类
            Contract contract = contractDao.selectByPrimaryKey(contractId);
            //5、设置合同的状态为已经报运，state=2
            contract.setState(2);
            //6、更新合同到数据库
            contractDao.updateByPrimaryKeySelective(contract);
            //--------------------------------------------更新合同状态
            //7、通过合同contractId查询合同货物列表
            ContractProductExample cpExample = new ContractProductExample();
            ContractProductExample.Criteria cpCriteria = cpExample.createCriteria();
            cpCriteria.andContractIdEqualTo(contractId);
            List<ContractProduct> cpList = contractProductDao.selectByExample(cpExample);

            //8、循环合同货物列表，得到合同货物contractProduct
            for (ContractProduct contractProduct : cpList) {
                //9、创建报运商品实体类
                ExportProduct exportProduct = new ExportProduct();
                //10、将合同货物信息写入到报运商品信息
                //exportProduct.setProductNo(contractProduct.getProductNo());
                BeanUtils.copyProperties(contractProduct, exportProduct);
                //11、设置报运单exportId
                exportProduct.setExportId(export.getId());
                //12、设置报运商品的id
                exportProduct.setId(UUID.randomUUID().toString());
                //13、将报运商品写入数据库
                exportProductDao.insertSelective(exportProduct);

                proNum++;

                //14、将报运商品列表设置到报运单中
                epList.add(exportProduct);
                //--------------------------------------------写入报运单商品

                //15、通过合同货物contractProduct.getId查询合同附件列表
                ExtCproductExample extCexample = new ExtCproductExample();
                ExtCproductExample.Criteria extcCriteria = extCexample.createCriteria();
                extcCriteria.andContractProductIdEqualTo(contractProduct.getId());
                List<ExtCproduct> extCList = extCproductDao.selectByExample(extCexample);
                //16、循环合同附件列表，得到合同附件extcProduct
                for (ExtCproduct extCproduct : extCList) {
                    //17、创建报运附件实体类
                    ExtEproduct extEproduct = new ExtEproduct();
                    //18、将合同附件信息写入到报运附件信息
                    BeanUtils.copyProperties(extCproduct, extEproduct);
                    //19、设置报运单exportId
                    extEproduct.setExportId(export.getId());
                    //20、设置报运单商品exportProductId
                    extEproduct.setExportProductId(exportProduct.getId());
                    //21、设置报运附件的id
                    extEproduct.setId(UUID.randomUUID().toString());
                    //22、将报运附件写入数据库
                    extEproductDao.insertSelective(extEproduct);

                    extNum++;
                    //--------------------------------------------写入报运单附件
                }
            }
        }
        //23、合计报运商品数量设置到报运单中
        export.setProNum(proNum);
        //24、合计报运附件数量设置到报运单中
        export.setExtNum(extNum);
        //25、设置报运单商品列表
        export.setExportProducts(epList);
        //26、将报运单写入数据库
        exportDao.insertSelective(export);
    }

    public void update(Export export) {
        exportDao.updateByPrimaryKeySelective(export);
        if (export.getExportProducts() != null) {
            for (ExportProduct exportProduct : export.getExportProducts()) {
                exportProductDao.updateByPrimaryKeySelective(exportProduct);
            }
        }
    }

    public void delete(String id) {

    }

    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }

    public void updateE(ExportResult exportResult) {
        //1.根据exportResult更新export实体类
        Export export = new Export();
        export.setId(exportResult.getExportId());
        export.setState(exportResult.getState());
        export.setRemark(exportResult.getRemark());
        //2.将export更新到数据库
        exportDao.updateByPrimaryKeySelective(export);
        //3.根据exportResult.products循环
        for (ExportProductResult productResult : exportResult.getProducts()) {
            //4.更新exportProduct实体类
            ExportProduct exportProduct = new ExportProduct();
            exportProduct.setId(productResult.getExportProductId());
            exportProduct.setTax(productResult.getTax());
            //5.将exportProduct更新到数据库
            exportProductDao.updateByPrimaryKeySelective(exportProduct);
        }
    }
}
