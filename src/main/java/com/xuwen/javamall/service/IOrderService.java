package com.xuwen.javamall.service;


import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;

public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);




}
