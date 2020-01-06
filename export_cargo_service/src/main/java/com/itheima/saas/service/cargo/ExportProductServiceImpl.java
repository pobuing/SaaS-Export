package com.itheima.saas.service.cargo;


import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.saas.dao.cargo.ExportProductDao;
import com.itheima.saas.domain.cargo.ExportProduct;
import com.itheima.saas.domain.cargo.ExportProductExample;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class ExportProductServiceImpl implements ExportProductService {

	@Autowired
	private ExportProductDao exportProductDao;

	@Override
	public List<ExportProduct> findAll(ExportProductExample example) {
		return exportProductDao.selectByExample(example);
	}
}
