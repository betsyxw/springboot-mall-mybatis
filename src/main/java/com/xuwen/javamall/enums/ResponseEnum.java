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
public enum ResponseEnum {
    SUCCESS(0,"成功"),
    PASSWORD_ERROR(1,"密码错误"),
    USER_EXIST(2,"用户已存在"),
    NEED_LOGIN(10,"用户未登录,请先登陆"),
    ERROR(-1,"服务端异常"),
        ;

    Integer code;
    String desc;

    ResponseEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }
}
