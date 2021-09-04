package com.xuwen.javamall.enums;

import lombok.Getter;

/**
 * author:xuwen
 * Created on 2021/9/4
 * 1-普通用户
 * 0-管理员
 */
//枚举类！！！enum
@Getter
public enum  RoleEnum {
    ADMIN(0),
    CUSTOMER(1),
        ;

    Integer code;

    RoleEnum(Integer code) {
        this.code = code;
    }
}
