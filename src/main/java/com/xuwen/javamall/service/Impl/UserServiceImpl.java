package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.UserMapper;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * author:xuwen
 * Created on 2021/9/4
 */


@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 注册
     * @param user
     * */
    @Override
    public void register(User user) {
        //1先查数据库，参数校验，用户名不能重复，邮箱不能重复，2写入数据库
        //查用户名
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername >0){
            throw new RuntimeException("该username用户名已经注册！");
        }
        //查email
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            throw new RuntimeException("该email已经注册");
        }

        //处理密码md5--springboot自带，DigestUtil包,选返回值string那个
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5DigestAsHex);

        //写入数据库
        //insertSelective有返回值
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
            throw new RuntimeException("注册失败");
        }



    }
}
