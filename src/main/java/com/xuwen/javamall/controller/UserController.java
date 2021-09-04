package com.xuwen.javamall.controller;

import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
    public ResponseVo register(@RequestBody User user){
        log.info("username={}",user.getUsername());
        return ResponseVo.success();
    }


}
