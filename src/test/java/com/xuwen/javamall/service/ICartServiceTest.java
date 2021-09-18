package com.xuwen.javamall.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;


@Slf4j
public class ICartServiceTest extends JavamallApplicationTests {

    @Autowired
    private ICartService cartService;

    private Gson gson = new  GsonBuilder().setPrettyPrinting().create();

    @Test
    public void add() {
        CartAddForm form = new CartAddForm();
        form.setProductId(29);
        form.setSelected(true);

        ResponseVo<CartVo> responseVo = cartService.add(1, form);
        log.info("list={}",gson.toJson(responseVo));
    }

    @Test
    public void test(){
        ResponseVo<CartVo> list = cartService.list(1);
        log.info("list={}",gson.toJson(list));
    }


}

