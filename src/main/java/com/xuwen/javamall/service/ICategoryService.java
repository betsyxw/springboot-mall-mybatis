package com.xuwen.javamall.service;


import com.xuwen.javamall.vo.CategoryVo;
import com.xuwen.javamall.vo.ResponseVo;


import java.util.List;

public interface ICategoryService {

    ResponseVo<List<CategoryVo>> selectAll();

}
