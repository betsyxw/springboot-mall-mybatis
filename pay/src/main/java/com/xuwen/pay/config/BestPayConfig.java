package com.xuwen.pay.config;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@Component
public class BestPayConfig {

    @Bean
    public BestPayService bestPayService(){
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("7mdApPMfXddfWWbbP4DUaVYm2wjyh3v3");
        wxPayConfig.setNotifyUrl("http://127.0.0.1");


        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);

        return bestPayService;
    }

}
