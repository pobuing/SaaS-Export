package com.itheima.saas.dao.baseinfo.systemcode;

import com.itheima.saas.domain.baseinfo.systemcode.SCProductNo;
import com.itheima.saas.domain.baseinfo.systemcode.SCProductNoExample;

import java.util.List;

public interface SCProductNoDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String productnumId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    int insert(SCProductNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    int insertSelective(SCProductNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    List<SCProductNo> selectByExample(SCProductNoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    SCProductNo selectByPrimaryKey(String productnumId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(SCProductNo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table sc_productno
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(SCProductNo record);
}