package com.xuwen.javamall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * author:xuwen
 * Created on 2021/9/4
 */
//JSON序列化，data是null的时候，不返回，追加注解
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Data
public class ResponseVo<T> {
    private Integer status;
    private String msg;
    private T data;


    //构造方法
    public ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //静态方法，msg提示
    public static <T> ResponseVo<T> success(String msg){
        return new ResponseVo(0,msg);
    }

}
