package com.xuwen.javamall.service;


import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.form.CartUpdateForm;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;


//接口
public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

}
