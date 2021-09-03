package com.xuwen.pay.controller;

import com.lly835.bestpay.model.PayResponse;
import com.xuwen.pay.service.Impl.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("amount") BigDecimal amount){
        PayResponse response = payService.create(orderId, amount);

        //为了传参数写个map
        Map map = new HashMap<>();
        map.put("codeUrl",response.getCodeUrl());
        return new ModelAndView("create",map);
    }


}
