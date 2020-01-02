package com.itheima.saas.service.cargo;


import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.Export;
import com.itheima.saas.domain.cargo.ExportExample;

import java.lang.reflect.InvocationTargetException;

public interface ExportService {

    Export findById(String id);

    void save(Export export) throws InvocationTargetException, IllegalAccessException;

    void update(Export export);

    void delete(String id);

    PageInfo findAll(ExportExample example, int page, int size);
}
