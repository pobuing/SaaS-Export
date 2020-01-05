package com.itheima.saas.dao.stat;

import java.util.List;
import java.util.Map;

/**
 * @author wangxin
 * @date 2020/1/3 15:48
 * @description: TODO
 * GOOD LUCK！
 */
public interface StatDao {
    List<Map> getFactoryData(String companyId);

    List<Map> getOnlineData(String companyId);

    List<Map> getSellData(String companyId);
}
