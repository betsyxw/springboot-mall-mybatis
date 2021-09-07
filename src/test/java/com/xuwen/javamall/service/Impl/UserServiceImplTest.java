package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.enums.RoleEnum;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import com.xuwen.javamall.vo.ResponseVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
//test是否回滚数据，@Transactional
//@Transactional
//测试方法类，需要继承父类测试方法，否则头上➕注解
public class UserServiceImplTest extends JavamallApplicationTests {
    public static final String USERNAME="jack";
    public static final String PASSWORD="123456";
    public static final String EMAIL="jack@qq.com";

    @Autowired
    private IUserService userService;

    //注册测试
    @Test
    public void register() {
        User user = new User("jack","123456","jack@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
        System.out.println("数据注册成功！");
    }

    //登陆测试
    @Test
    public void login(){
        register();
        ResponseVo<User> responseVo = userService.login(USERNAME, PASSWORD);
        Assert.assertEquals(ResponseEnum.SUCCESS.getCode(),responseVo.getStatus());


    }
}