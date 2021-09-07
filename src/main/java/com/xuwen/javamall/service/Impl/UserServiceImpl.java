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
    public ResponseVo<User> register(User user) {
        //错误测试，自己下面写个方法
       //error();

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
//        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8));
//        user.setPassword(md5DigestAsHex);
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)
        ));


        //写入数据库
        //insertSelective有返回值
        int resultCount = userMapper.insertSelective(user);
        if(resultCount==0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        return ResponseVo.success();

    }

    /**
     * 登陆
     * @param
     * */
    //登陆
    @Override
    public ResponseVo<User> login(String username, String password) {
        User user = userMapper.selectByUsername(username);
        if(user == null){
            //用户不存在(返回，用户名或者密码错误，统一)
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        //数据库password，是否等于用户输入的password
        if (!user.getPassword().equalsIgnoreCase(
                DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8)))) {
            //密码错误(返回，用户名或者密码错误，统一)
            return ResponseVo.error(ResponseEnum.USERNAME_OR_PASSWORD_ERROR);
        }
        //返回，成功！
        return ResponseVo.success(user);
    }


    //模拟一个错误，仍一个运行时exception
    private void error(){
        throw new RuntimeException("意外错误！");
    }



}
