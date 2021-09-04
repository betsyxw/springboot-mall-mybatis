package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.UserMapper;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.enums.RoleEnum;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.IUserService;
import com.xuwen.javamall.vo.ResponseVo;
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
    public ResponseVo register(User user) {
        //错误测试，自己下面写个方法
//        error();

        //1先查数据库，参数校验，用户名不能重复，邮箱不能重复，2写入数据库
        //查用户名
        int countByUsername = userMapper.countByUsername(user.getUsername());
        if(countByUsername >0){
           return ResponseVo.error(ResponseEnum.USERNAME_EXIST);
        }
        //查email
        int countByEmail = userMapper.countByEmail(user.getEmail());
        if(countByEmail>0){
            return ResponseVo.error(ResponseEnum.EMAIL_EXIST);
        }

        //一定要设置用户角色，字段！
        user.setRole(RoleEnum.CUSTOMER.getCode());

        //处理密码md5--springboot自带，DigestUtil包,选返回值string那个
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
        user.setPassword(md5DigestAsHex);

        //写入数据库
        //insertSelective有返回值
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();

    }

    //模拟一个错误，仍一个运行时exception
    private void error(){
        throw new RuntimeException("意外错误！");
    }


}
