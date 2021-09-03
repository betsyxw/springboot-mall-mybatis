package com.xuwen.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@Controller
@RequestMapping("/pay")
public class PayController {

    @GetMapping("/create")
    public ModelAndView create(){
//        Map map = new HashMap<>();
//        map.put("codeUrl","weixin://wxpay/bizpayurl?pr=0DN6riNzz");
        return new ModelAndView("create");
    }


}
