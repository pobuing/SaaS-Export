package com.itheima.saas.service.stat.cargo;


import com.itheima.saas.domain.cargo.ExportProduct;
import com.itheima.saas.domain.cargo.ExportProductExample;

import java.util.List;

public interface ExportProductService {

    //根据条件查询
    List<ExportProduct> findAll(ExportProductExample example);
}
