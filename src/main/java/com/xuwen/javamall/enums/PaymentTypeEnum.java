package com.xuwen.javamall.enums;

import lombok.Getter;

/**
 * author:xuwen
 * Created on 2021/9/23
 */
@Getter
public enum PaymentTypeEnum {

    PAY_ONLINE(1),

    ;

    Integer code;

    PaymentTypeEnum(Integer code){
        this.code = code;
    }

}
