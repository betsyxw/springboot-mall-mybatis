package com.xuwen.javamall.controller;

import com.xuwen.javamall.consts.MallConst;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.UserLoginForm;
import com.xuwen.javamall.form.UserRegisterForm;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Objects;

/**
 * author:xuwen
 * Created on 2021/9/4
 */

@RestController
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;

    //注册
    //前端传来的信息必需是JSON格式的！！！
    @PostMapping("/user/register")
    public ResponseVo<User> register(@Valid @RequestBody UserRegisterForm userForm){
        User user = new User();
        BeanUtils.copyProperties(userForm,user);
        return userService.register(user);
    }

    //登陆controller
    //传入的参数，重新新建一个类，不要耦合
    @PostMapping("/user/login")
    public ResponseVo<User> login(@Valid @RequestBody UserLoginForm userLoginForm,
                                   HttpSession session){
        ResponseVo<User> userResponseVo = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
        //成功登陆之后，//set--session
        session.setAttribute(MallConst.CURRENT_USER,userResponseVo.getData());
        log.info("/login sessionId={}",session.getId());

        return userResponseVo;

    }

    //测试session
    //改进版本，token+redis
    @GetMapping("/user")
    public ResponseVo<User> userInfo(HttpSession session){
        log.info("/user sessionId={}",session.getId());
        //get--session
        //判断是否登陆状态
       User user = (User)session.getAttribute(MallConst.CURRENT_USER);
        //移除，登陆判断，使用拦截器代替

       return ResponseVo.success(user);


    }

    //判断登陆状态，使用拦截器TODO
    //登出
    @PostMapping("/user/logout")
    public ResponseVo userinfo(HttpSession session){
        log.info("/user/logout sessionId={}",session.getId());
        //判断是否登陆状态-->换成拦截器

        session.removeAttribute(MallConst.CURRENT_USER);
        return ResponseVo.success();
    }


}
