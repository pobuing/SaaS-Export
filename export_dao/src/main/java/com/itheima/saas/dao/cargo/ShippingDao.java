package com.itheima.saas.dao.cargo;

import com.itheima.saas.domain.cargo.Shipping;
import com.itheima.saas.domain.cargo.ShippingExample;

import java.util.List;

public interface ShippingDao {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String shippingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    int insert(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    int insertSelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    List<Shipping> selectByExample(ShippingExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    Shipping selectByPrimaryKey(String shippingId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Shipping record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table co_shipping
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Shipping record);
}