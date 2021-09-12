package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.pojo.Product;
import com.xuwen.javamall.service.ICategoryService;
import com.xuwen.javamall.service.IProductService;
import com.xuwen.javamall.vo.ProductVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * author:xuwen
 * Created on 2021/9/12
 */
@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ResponseVo<List<ProductVo>> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        List<Product> products = productMapper.selectByCategoryIdSet(categoryIdSet);
        log.info("products={}",products);

        return null;
    }
}
