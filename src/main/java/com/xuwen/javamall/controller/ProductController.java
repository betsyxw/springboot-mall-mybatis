package com.xuwen.javamall.controller;

import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.service.IProductService;
import com.xuwen.javamall.vo.ProductDetailVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:xuwen
 * Created on 2021/9/12
 */

@RestController
public class ProductController {
    /*
*controlller调用servie，先注入
service数据
* */
    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    public ResponseVo<PageInfo> list(@RequestParam(required = false) Integer categoryId,
                                     @RequestParam(required = false,defaultValue = "1")Integer PageNum,
                                     @RequestParam(required = false,defaultValue = "10")Integer PageSize){

        return productService.list(categoryId, PageNum, PageSize);
    }

    //商品详情页
    @GetMapping("/products/{productId}")
    public ResponseVo<ProductDetailVo> detail(@PathVariable Integer productId){
        return productService.detail(productId);
    }




}
