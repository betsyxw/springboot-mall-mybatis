package com.xuwen.pay.service.Impl;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xuwen.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@Slf4j
@Service
public class PayService implements IPayService {

    @Override
    public void create(String orderId, BigDecimal amount) {
        WxPayConfig wxPayConfig = new WxPayConfig();
        wxPayConfig.setAppId("wxd898fcb01713c658");
        wxPayConfig.setMchId("1483469312");
        wxPayConfig.setMchKey("7mdApPMfXddfWWbbP4DUaVYm2wjyh3v3");
        wxPayConfig.setNotifyUrl("http://127.0.0.1");


        BestPayServiceImpl bestPayService = new BestPayServiceImpl();
        bestPayService.setWxPayConfig(wxPayConfig);


        PayRequest request = new PayRequest();
        request.setOrderName("3977952-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}",response);

    }


}
