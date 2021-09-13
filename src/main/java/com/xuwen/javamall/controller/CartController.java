package com.xuwen.javamall.controller;

import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * author:xuwen
 * Created on 2021/9/13
 */
/**
 * 表单统一验证
 */

@RestController
public class CartController {

    /**
     * 入参：CartAddFrom
     * 返回值：ResponseVo<CartVo>
     * */
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm){

        return null;
    }
}
