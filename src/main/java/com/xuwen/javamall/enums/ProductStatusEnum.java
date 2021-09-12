package com.xuwen.javamall.enums;

import lombok.Getter;

/**
 * author:xuwen
 * 商品状态1在售，2下架，3删除
 * Created on 2021/9/12
 */
@Getter
public enum ProductStatusEnum {
    ON_SALE(1),
    OFF_SALE(2),
    DELETE(3),
    ;

    Integer code;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }
}
