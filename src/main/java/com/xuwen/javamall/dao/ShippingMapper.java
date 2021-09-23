package com.xuwen.javamall.dao;

import com.xuwen.javamall.pojo.Shipping;
import org.apache.ibatis.annotations.Param;

public interface ShippingMapper {

    int deleteByPrimaryKey(Integer id);

    int deleteByIdAndUid(@Param("uid") Integer uid,
                         @Param("shippingId") Integer shippingId);

    int insert(Shipping record);

    int insertSelective(Shipping record);

    Shipping selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Shipping record);

    int updateByPrimaryKey(Shipping record);
}