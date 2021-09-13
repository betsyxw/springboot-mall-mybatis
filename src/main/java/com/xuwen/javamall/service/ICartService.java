package com.xuwen.javamall.service;


import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;


//接口
public interface ICartService {

    ResponseVo<CartVo> add(CartAddForm form);

}
