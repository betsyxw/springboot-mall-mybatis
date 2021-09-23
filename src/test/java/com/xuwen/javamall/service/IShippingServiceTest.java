package com.xuwen.javamall.service;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.ShippingForm;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

import static org.junit.Assert.*;

@Slf4j
public class IShippingServiceTest extends JavamallApplicationTests {
    @Autowired
    private IShippingService shippingService;

    private Integer uid = 1;

    private ShippingForm form;

    private Integer shippingId = 8;

    @Before
    public void before(){
        ShippingForm form = new ShippingForm();
        form.setReceiverName("张三");
        form.setReceiverAddress("文酱6");
        form.setReceiverCity("上海");
        form.setReceiverDistrict("浦东新区");
        form.setReceiverMobile("1331991919");
        form.setReceiverPhone("01000000222");
        form.setReceiverProvince("上海");
        form.setReceiverZip("200010");
        this.form = form;
    }

    @Test
    public void add() {

        ResponseVo<Map<String, Integer>> responseVo = shippingService.add(uid, form);
        log.info("result={}"+responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void delete() {

        ResponseVo responseVo = shippingService.delete(uid, shippingId);
        log.info("result={}"+responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }

    @Test
    public void update() {
        form.setReceiverZip("081001");
        form.setReceiverName("张三");
        form.setReceiverPhone("100086");
        form.setReceiverProvince("杭州");
        ResponseVo responseVo = shippingService.update(uid,shippingId,form);
        log.info("result={}"+responseVo);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());


    }

    @Test
    public void list() {
    }
}