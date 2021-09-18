package com.xuwen.javamall.controller;

import com.xuwen.javamall.consts.MallConst;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.form.CartUpdateForm;
import com.xuwen.javamall.pojo.User;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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
    /***
     * 注入接口+实现类
     * */
    @Autowired
    private ICartService cartService;

    /**
     * 获取购物车列表
     * */
    @GetMapping("/carts")
    public ResponseVo<CartVo> list(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.list(user.getId());
    }

    /**
     * 新增
     * 入参：CartAddFrom
     * 返回值：ResponseVo<CartVo>
     * */
    @PostMapping("/carts")
    public ResponseVo<CartVo> add(@Valid @RequestBody CartAddForm cartAddForm, HttpSession session){

        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.add(user.getId(),cartAddForm);
    }

    @PutMapping("/carts/{productId}")
    public ResponseVo<CartVo> update(@PathVariable Integer productId,
                                     @Valid @RequestBody CartUpdateForm form,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.update(user.getId(),productId,form);
    }

    @DeleteMapping("/carts/{productId}")
    public ResponseVo<CartVo> delete(@PathVariable Integer productId,
                                     HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.delete(user.getId(),productId);
    }

    @PutMapping("/carts/selectAll")
    public ResponseVo<CartVo> selectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.selectAll(user.getId());
    }

    @PutMapping("/carts/unSelectAll")
    public ResponseVo<CartVo> unSelectAll(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.unSelectAll(user.getId());
    }

    @GetMapping("/carts/products/sum")
    public ResponseVo<Integer> sum(HttpSession session){
        User user = (User) session.getAttribute(MallConst.CURRENT_USER);
        return cartService.sum(user.getId());
    }


}
