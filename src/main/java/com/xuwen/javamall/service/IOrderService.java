package com.xuwen.javamall.service;


import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;

public interface IOrderService {

    ResponseVo<OrderVo> create(Integer uid, Integer shippingId);

    //订单列表
    ResponseVo<PageInfo> list(Integer uid,Integer pageNum, Integer pageSize);

    //订单详情
    ResponseVo<OrderVo> detail(Integer uid, Long orderNo);

    //取消订单
    ResponseVo cancel(Integer uid, Long orderNo);





}
