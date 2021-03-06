package com.xuwen.javamall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.pojo.Product;
import com.xuwen.javamall.service.ICategoryService;
import com.xuwen.javamall.service.IProductService;
import com.xuwen.javamall.vo.ProductDetailVo;
import com.xuwen.javamall.vo.ProductVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xuwen.javamall.enums.ProductStatusEnum.OFF_SALE;
import static com.xuwen.javamall.enums.ProductStatusEnum.DELETE;
import static com.xuwen.javamall.enums.ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE;

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
    public ResponseVo<PageInfo> list(Integer categoryId, Integer pageNum, Integer pageSize) {
        Set<Integer> categoryIdSet = new HashSet<>();
        if(categoryId != null){
            categoryService.findSubCategoryId(categoryId,categoryIdSet);
            categoryIdSet.add(categoryId);
        }
        //pageHelper
        PageHelper.startPage(pageNum,pageSize);
        //productList是数据库中，查出的信息！
        List<Product> productList = productMapper.selectByCategoryIdSet(categoryIdSet);
        List<ProductVo> productVoList = productList.stream()
                .map(e -> {
                    ProductVo productVo = new ProductVo();
                    BeanUtils.copyProperties(e, productVo);
                    return productVo;
                })
                .collect(Collectors.toList());

        PageInfo pageInfo = new PageInfo<>(productList);
        pageInfo.setList(productVoList);
        return ResponseVo.success(pageInfo);
    }


    //商品详情页
    @Override
    public ResponseVo<ProductDetailVo> detail(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        if(product.getStatus().equals(OFF_SALE.getCode()) || product.getStatus().equals(DELETE.getCode())){
            return ResponseVo.error(PRODUCT_OFF_SALE_OR_DELETE);
        }
        ProductDetailVo productDetailVo = new ProductDetailVo();
        BeanUtils.copyProperties(product,productDetailVo);

        return ResponseVo.success(productDetailVo);
    }
}
