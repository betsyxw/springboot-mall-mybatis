package com.xuwen.javamall.service;


import com.xuwen.javamall.vo.ProductVo;
import com.xuwen.javamall.vo.ResponseVo;

import java.util.List;

public interface IProductService {

    //返回值，方法名，参数
    ResponseVo<List<ProductVo>> list(Integer categoryId,Integer pageNum,Integer pageSize);
}
