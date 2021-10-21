package com.xuwen.javamall.controller;


import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.consts.MallConst;
import com.xuwen.javamall.form.OrderCreateForm;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IOrderService;
import com.xuwen.javamall.service.Impl.OrderServiceImpl;
import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * author:xuwen
 * Created on 2021/10/21
 */
@RestController
public class OrderController {
    //接口，实现类
    @Autowired
    private IOrderService orderService;

    //创建订单
    @PostMapping("/orders")
    public ResponseVo<OrderVo> create(@Valid @RequestBody OrderCreateForm form, HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
       return  orderService.create(user.getId(), form.getShippingId());
    }

    //订单列表
    @GetMapping("/orders")
    public ResponseVo<PageInfo> list(@RequestParam Integer pageNum,@RequestParam Integer pageSize,HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.list(user.getId(),pageNum,pageSize);
    }



    //订单详情
    @GetMapping("/orders/{orderNo}")
    public ResponseVo<OrderVo> detail(@PathVariable Long orderNo,HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.detail(user.getId(),orderNo);

    }

    //取消订单
    @PutMapping("/orders/{orderNo}")
    public ResponseVo cancel(@PathVariable Long orderNo,HttpSession session){
        User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        return orderService.cancel(user.getId(),orderNo);
    }



}
