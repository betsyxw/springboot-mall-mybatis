package com.xuwen.javamall.controller;

import com.xuwen.javamall.service.ICategoryService;
import com.xuwen.javamall.vo.CategoryVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author:xuwen
 * Created on 2021/9/7
 */

@RestController
public class CategoryController {
    @Autowired
    private ICategoryService categoryService;

    @GetMapping("/categories")
    public ResponseVo<List<CategoryVo>> selectAll(){

        return categoryService.selectAll();
    }

}
