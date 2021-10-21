package com.xuwen.javamall.dao;

import com.xuwen.javamall.pojo.Order;

import java.util.List;

public interface OrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

    //用户uid
    List<Order> selectByUid(Integer uid);

    //通过OrderNo查
    Order selectByOrderNo(Long orderNo);

}