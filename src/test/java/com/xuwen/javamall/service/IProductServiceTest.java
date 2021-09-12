package com.xuwen.javamall.service;

import com.xuwen.javamall.JavamallApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


public class IProductServiceTest extends JavamallApplicationTests {

    @Autowired
    private IProductService productService;

    @Test
    public void list() {
        productService.list(100002,1,1);

    }
}