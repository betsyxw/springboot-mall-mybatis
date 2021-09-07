package com.xuwen.javamall.service;


import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.vo.ResponseVo;

public interface IUserService {

    /**
     * 注册
     * */
    ResponseVo<User> register(User user);



    /**
     * 登陆
     * */
    ResponseVo<User> login(String username,String password);




}
