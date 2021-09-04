package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.enums.RoleEnum;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

//测试方法类，需要继承父类测试方法，否则头上➕注解
public class UserServiceImplTest extends JavamallApplicationTests {
    @Autowired
    private IUserService userService;

    @Test
    public void register() {
        User user = new User("zhangsan","123456","zhangsan@qq.com", RoleEnum.CUSTOMER.getCode());
        userService.register(user);
        System.out.println("数据注册成功！");
    }
}