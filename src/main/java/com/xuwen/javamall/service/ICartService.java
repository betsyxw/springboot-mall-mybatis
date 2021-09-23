package com.xuwen.javamall.service;


import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.form.CartUpdateForm;
import com.xuwen.javamall.pojo.Cart;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;

import java.util.List;


//接口
public interface ICartService {

    ResponseVo<CartVo> add(Integer uid,CartAddForm form);

    ResponseVo<CartVo> list(Integer uid);

    ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form);

    ResponseVo<CartVo> delete(Integer uid, Integer productId);

    ResponseVo<CartVo> selectAll(Integer uid);

    ResponseVo<CartVo> unSelectAll(Integer uid);

    ResponseVo<Integer> sum(Integer uid);

    List<Cart> listForCart(Integer uid);

}
