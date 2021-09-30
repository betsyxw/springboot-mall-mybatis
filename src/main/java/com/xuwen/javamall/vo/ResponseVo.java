package com.xuwen.javamall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.xuwen.javamall.enums.ResponseEnum;
import lombok.Data;
import org.springframework.validation.BindingResult;

import java.util.Objects;

/**
 * author:xuwen
 * Created on 2021/9/4
 * 模版：
 * 404
 * 服务器错误
 * data:[{
 *
 * },{
 *
 * },{
 *
 * }]
 */
//JSON序列化，data是null的时候，不返回，追加注解
@JsonInclude(value=JsonInclude.Include.NON_NULL)
@Data
public class ResponseVo<T> {
    private Integer status;
    private String msg;
    private T data;


    //构造方法
    private ResponseVo(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    //构造方法
    private ResponseVo(Integer status, T data) {
        this.status = status;
        this.data = data;
    }

    //静态方法，msg提示
    public static <T> ResponseVo<T> success(String msg){

        return new ResponseVo(ResponseEnum.SUCCESS.getCode(),msg);
    }

    //静态方法，data
    public static <T> ResponseVo<T> success(T data){

        return new ResponseVo(ResponseEnum.SUCCESS.getCode(),data);
    }


    //静态方法，重载
    public static <T> ResponseVo<T> success(){
        return new ResponseVo(ResponseEnum.SUCCESS.getCode(),ResponseEnum.SUCCESS.getDesc());
    }

    //静态方法，error1
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum){
        return new ResponseVo(responseEnum.getCode(),responseEnum.getDesc());
    }

    //静态方法，error2
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum,String msg){
        return new ResponseVo(responseEnum.getCode(),msg);
    }

    //静态方法，error3
    public static <T> ResponseVo<T> error(ResponseEnum responseEnum, BindingResult bindingResult){
        return new ResponseVo<>(responseEnum.getCode(),
                Objects.requireNonNull(bindingResult.getFieldError()).getField()+" "+bindingResult.getFieldError().getDefaultMessage());
    }

}
