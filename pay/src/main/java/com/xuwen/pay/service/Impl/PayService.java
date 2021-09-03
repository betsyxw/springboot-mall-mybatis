package com.xuwen.pay.service.Impl;

import com.lly835.bestpay.config.WxPayConfig;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.xuwen.pay.service.IPayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/3
 */

@Slf4j
@Service
public class PayService implements IPayService {

    //自己写个config，Bean注入一下，不用一直new
    @Autowired
    private BestPayService bestPayService;

    //写入数据库
    @Override
    public PayResponse create(String orderId, BigDecimal amount) {

        PayRequest request = new PayRequest();
        request.setOrderName("3977952-最好的支付sdk");
        request.setOrderId(orderId);
        request.setOrderAmount(amount.doubleValue());
        request.setPayTypeEnum(BestPayTypeEnum.WXPAY_NATIVE);

        PayResponse response = bestPayService.pay(request);
        log.info("response={}",response);

        return response;

    }



    //异步通知，方法重写
    @Override
    public String asyncNotify(String notifyData) {
        //1签名校验+订单金额校验
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("payResponse=>",payResponse);


        //2从数据库中查订单，核对订单金额


        //3修改订单状态


        //4告诉微信,代码来自官网文档：https://pay.weixin.qq.com/wiki/doc/api/index.html
        return "<xml>\n" +
                "  <return_code><![CDATA[SUCCESS]]></return_code>\n" +
                "  <return_msg><![CDATA[OK]]></return_msg>\n" +
                "</xml>";



    }






}
