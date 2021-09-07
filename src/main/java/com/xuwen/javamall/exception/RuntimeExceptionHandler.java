package com.xuwen.javamall.exception;

import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * author:xuwen
 * Created on 2021/9/4
 */
//为了返回，JSON格式，一定要+ResponseBody
@ControllerAdvice
public class RuntimeExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public ResponseVo handle(RuntimeException e){

        return ResponseVo.error(ResponseEnum.ERROR,e.getMessage());
    }

    @ExceptionHandler(UserloginException.class)
    @ResponseBody
    public ResponseVo userLoginHandle(){
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }
}
