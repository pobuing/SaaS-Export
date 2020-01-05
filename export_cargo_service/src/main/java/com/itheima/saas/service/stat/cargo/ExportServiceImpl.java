package com.itheima.saas.service.stat.cargo;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itheima.saas.dao.cargo.*;
import com.itheima.saas.domain.cargo.*;
import com.itheima.saas.domain.vo.ExportProductResult;
import com.itheima.saas.domain.vo.ExportResult;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {


    @Autowired
    private ExportDao exportDao;

    @Autowired
    private ContractDao contractDao;

    @Autowired
    private ContractProductDao contractProductDao; //合同货物dao

    @Autowired
    private ExportProductDao exportProductDao;//报运单商品dao

    @Autowired
    private ExtCproductDao extCproductDao;  //合同附件Dao

    @Autowired
    private ExtEproductDao extEproductDao;  //报运单附件Dao


    //保存

    /**
     * export对象：
     * contractIds : 所有勾选的合同id （已“，”隔开）
     */
    public void save(Export export) throws InvocationTargetException, IllegalAccessException {
        //I.保存报运单
        //1.设置报运单id
        export.setId(UUID.randomUUID().toString());
        //2.获取已勾选的合同id字符串 ： 1，2，3
        String contractIds = export.getContractIds();
        String[] ids = contractIds.split(",");
        //3.根据购销合同的id查询所有购销合同
        ContractExample contractExample = new ContractExample();
        ContractExample.Criteria contractCriteria = contractExample.createCriteria();
        contractCriteria.andIdIn(Arrays.asList(ids)); //将数组转化为list
        List<Contract> list = contractDao.selectByExample(contractExample);
        //4.拼接合同号集合的字符串
        String contractNos = "";
        for (Contract contract : list) {
            contractNos += contract.getContractNo() + " ";
            //修改合同状态 ： 将状态修改为已报运（2）
            contract.setState(2);
            contractDao.updateByPrimaryKeySelective(contract);
        }
        export.setCustomerContract(contractNos);
        //II.保存报运单商品

        Map<String, String> map = new HashMap<String, String>(); //货物id 和 商品id之间的关系    hw02  -- sp02

        //1.根据合同id查询所有的货物
        ContractProductExample productExample = new ContractProductExample();
        ContractProductExample.Criteria productCriteria = productExample.createCriteria();
        productCriteria.andContractIdIn(Arrays.asList(ids));
        List<ContractProduct> cps = contractProductDao.selectByExample(productExample);
        //2.循环所有的货物，创建报运单商品
        for (ContractProduct cp : cps) {
            //3.设置商品id ，设置报运单的（ID）外键 和其他的属性
            ExportProduct ep = new ExportProduct();
            BeanUtils.copyProperties(cp, ep); //将cp中的同名属性的数据 copy 到ep中的同名属性上
            ep.setId(UUID.randomUUID().toString());
            ep.setExportId(export.getId());
            //4.保存报运单商品
            exportProductDao.insertSelective(ep);
            map.put(cp.getId(), ep.getId());
        }
        //III.保存报运单附件
        //1.根据合同id查询所有的合同附件
        ExtCproductExample example = new ExtCproductExample();
        ExtCproductExample.Criteria criteria = example.createCriteria();
        criteria.andContractIdIn(Arrays.asList(ids));
        List<ExtCproduct> extcs = extCproductDao.selectByExample(example);
        //2.循环所有的合同附件，创建报运单附件
        for (ExtCproduct extc : extcs) {        //购销合同附件   货物id ： hw02
            ExtEproduct exte = new ExtEproduct();
            //3.设置普通属性
            BeanUtils.copyProperties(extc, exte);
            //4.设置id，报运单id，所属商品id
            exte.setId(UUID.randomUUID().toString());
            exte.setExportId(export.getId());
            //所属商品id
            String pid = map.get(extc.getContractProductId());// hw02货物id   --  商品的id sp02
            exte.setExportProductId(pid); //设置商品id
            //5.保存报运单附件
            extEproductDao.insertSelective(exte);
        }


        //保存报运单
        export.setState(0);//设置草稿状态
        export.setProNum(cps.size());
        export.setExtNum(extcs.size());
        exportDao.insertSelective(export);
    }

    //更新
    public void update(Export export) {
        //1.更新报运单
        exportDao.updateByPrimaryKeySelective(export);
        //2.更新报运单中的每一个商品数据
        for (ExportProduct ep : export.getExportProducts()) {
            exportProductDao.updateByPrimaryKeySelective(ep);
        }
    }

    //删除
    public void delete(String id) {

    }


    //根据id查询
    public Export findById(String id) {
        return exportDao.selectByPrimaryKey(id);
    }

    //分页
    public PageInfo findAll(ExportExample example, int page, int size) {
        PageHelper.startPage(page, size);
        List<Export> list = exportDao.selectByExample(example);
        return new PageInfo(list);
    }


    @Override
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
