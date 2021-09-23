package com.xuwen.javamall.service;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

@Slf4j
public class IOrderServiceTest extends JavamallApplicationTests {
    @Autowired
    private IOrderService orderService;

    private Integer uid = 1;

    private Integer shippingId = 4;

    @Test
    public void create() {
        ResponseVo<OrderVo> responseVo = orderService.create(uid, shippingId);
        log.info("responseVo={}",responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}