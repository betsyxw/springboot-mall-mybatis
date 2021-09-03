package com.xuwen.pay.service;

import com.lly835.bestpay.model.PayResponse;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/3
 */
public interface IPayService {
    /**
     * 发起支付
     * */
    PayResponse create(String orderId, BigDecimal amount);


}
