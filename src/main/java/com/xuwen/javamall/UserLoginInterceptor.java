package com.xuwen.javamall;

import com.xuwen.javamall.consts.MallConst;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.exception.UserloginException;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * author:xuwen
 * Created on 2021/9/7
 */

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {
    /**
     * true表示，继续流程，false表示中断
     * **/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        //判断是否登陆状态
        User user = (User)request.getSession().getAttribute(MallConst.CURRENT_USER);
        if(user == null){
            log.info("user==null");
            throw new UserloginException();
            //return false;
            //用户未登录-》需要登陆
            //需要返回的信息，用异常抛出，再捕获,再处理
            //return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return true;
    }
}
