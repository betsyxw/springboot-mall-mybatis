package com.xuwen.javamall.service;


import com.xuwen.javamall.vo.CategoryVo;
import com.xuwen.javamall.vo.ResponseVo;


import java.util.List;
import java.util.Set;

public interface ICategoryService {

    //查询所有数据
    ResponseVo<List<CategoryVo>> selectAll();

    //查询子类目ID
    void findSubCategoryId(Integer id, Set<Integer> resultSet);

}
