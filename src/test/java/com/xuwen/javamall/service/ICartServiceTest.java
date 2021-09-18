package com.xuwen.javamall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.form.CartUpdateForm;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


@Slf4j
public class ICartServiceTest extends JavamallApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new  GsonBuilder().setPrettyPrinting().create();

    private Integer productId = 26;

    private Integer uid = 1;

    @Before
    public void add() {
        CartAddForm form = new CartAddForm();
        form.setProductId(productId);
        form.setSelected(true);

        ResponseVo<CartVo> responseVo = cartService.add(uid, form);
        log.info("list={}",gson.toJson(responseVo));
    }

    @Test
    public void list(){
        ResponseVo<CartVo> list = cartService.list(uid);
        log.info("list={}",gson.toJson(list));
    }

    @Test
    public void update(){
        CartUpdateForm form = new CartUpdateForm();
        form.setQuantity(5);
        form.setSelected(false);
        ResponseVo<CartVo> responseVo = cartService.update(uid, productId,form);
        log.info("result={}",gson.toJson(responseVo));

    }

    @After
    public void delete(){
        ResponseVo<CartVo> responseVo = cartService.delete(uid, productId);
        log.info("result={}",gson.toJson(responseVo));

    }

    @Test
    public void selectAll(){
        ResponseVo<CartVo> responseVo = cartService.selectAll(uid);
        log.info("result={}",gson.toJson(responseVo));

    }


    @Test
    public void unSelectAll(){
        ResponseVo<CartVo> responseVo = cartService.unSelectAll(uid);
        log.info("result={}",gson.toJson(responseVo));

    }

    @Test
    public void sum(){
        ResponseVo<Integer> responseVo = cartService.sum(uid);
        log.info("result={}",gson.toJson(responseVo));

    }


}

