package com.xuwen.pay.service;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/3
 */
public interface IPayService {
    /**
     * 发起支付
     * */
    void create(String orderId, BigDecimal amount);


}
