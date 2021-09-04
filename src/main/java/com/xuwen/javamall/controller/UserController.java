package com.xuwen.javamall.controller;

import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.UserForm;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * author:xuwen
 * Created on 2021/9/4
 */

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    //注册
    //前端传来的信息必需是JSON格式的！！！
    @PostMapping("/register")
    public ResponseVo register(@Valid @RequestBody UserForm userForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("注册提交的参数有误{}{}=>", Objects.requireNonNull(bindingResult.getFieldError()).getField(),bindingResult.getFieldError().getDefaultMessage());
            return ResponseVo.error(ResponseEnum.PARAM_ERROR,bindingResult);
        }
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        return userService.register(user);
    }


}
