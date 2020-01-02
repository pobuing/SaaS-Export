package com.itheima.saas.service.cargo;

import com.github.pagehelper.PageInfo;
import com.itheima.saas.domain.cargo.ExtCproduct;
import com.itheima.saas.domain.cargo.ExtCproductExample;

/**
 * @author wangxin
 * @date 2019/12/30 19:37
 * @description: TODO
 * GOOD LUCK！
 * 附件接口
 */
public interface ExtCproductService {
    PageInfo findAll(ExtCproductExample extCproductExample, int page, int size);

    ExtCproduct findById(String id);

    void save(ExtCproduct extCproduct);

    void update(ExtCproduct extCproduct);

    void delete(String id);

}
