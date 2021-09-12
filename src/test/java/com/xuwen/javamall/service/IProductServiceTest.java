package com.xuwen.javamall.service;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.vo.ProductVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;


public class IProductServiceTest extends JavamallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
      ResponseVo<List<ProductVo>> responseVo =  productService.list(null,1,1);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());
    }
}