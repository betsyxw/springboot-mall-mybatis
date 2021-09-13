package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.enums.ProductStatusEnum;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.pojo.Product;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.xuwen.javamall.enums.ResponseEnum.PRODUCT_NOT_EXIST;

/**
 * author:xuwen
 * Created on 2021/9/13
 */

@Service
public class CartServiceImpl implements ICartService {
    @Autowired
    private ProductMapper productMapper;


    /**
     * form入参
     * 返回值ResopnseVo<CartVo>
     * */
    @Override
    public ResponseVo<CartVo> add(CartAddForm form) {
        Product product = productMapper.selectByPrimaryKey(form.getProductId());
        //商品是否存在->数据库查商品，用到ProductMap
        if(product==null){
            return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST);
        }
        //商品status，是否在售,比code
        if(!product.getStatus().equals(ProductStatusEnum.ON_SALE.getCode())){
            return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE);
        }
        //商品库存是否充足
        if(product.getStock()<=0){
            return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR);
        }
        //三层校验完毕后，写入redis


        return null;
    }
}
