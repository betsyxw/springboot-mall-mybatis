package com.xuwen.javamall;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author:xuwen
 * Created on 2021/9/7
 * 别忘记把/carts购物车接口去掉，需要登陆校验才能操作购物车
 */
//自定义扩展mvc的功能，step1：@Configuration，step2：implements WebMvcConfigure
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/error","/user/login","/user/register","/categories","/products","/products/*","/carts","/carts/*","/js/**","/css/**","/img/**");

    }
}
