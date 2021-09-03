package com.xuwen.pay.service.Impl;

import com.xuwen.pay.PayApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;


public class PayServiceTest extends PayApplicationTests {

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        payService.create("1234999567", BigDecimal.valueOf(0.01));


    }
}