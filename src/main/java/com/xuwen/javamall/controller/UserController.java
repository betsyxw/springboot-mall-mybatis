package com.xuwen.javamall.controller;

import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.UserForm;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * author:xuwen
 * Created on 2021/9/4
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    //注册
    //前端传来的信息必需是JSON格式的！！！
    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("注册提交的参数有误{}{}=>",bindingResult.getFieldError().getField(),bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult.getFieldError().getField()+" "+bindingResult.getFieldError().getDefaultMessage());
        }

        log.info("username={}",userForm.getUsername());
//        return ResponseVo.success();
        return ResponseVo.error(ResponseEnum.NEED_LOGIN);
    }


}
