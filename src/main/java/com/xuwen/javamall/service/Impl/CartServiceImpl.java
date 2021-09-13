package com.xuwen.javamall.service.Impl;

import com.google.gson.Gson;
import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.enums.ProductStatusEnum;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.pojo.Cart;
import com.xuwen.javamall.pojo.Product;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import static com.xuwen.javamall.enums.ResponseEnum.PRODUCT_NOT_EXIST;

/**
 * author:xuwen
 * Created on 2021/9/13
 */

@Service
public class CartServiceImpl implements ICartService {
    private final static String CART_REDIS_KEY_TEMPLATE = "cart_%d";

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //fastjson同款，gson谷歌的
    private Gson gson = new Gson();


    /**
     * form入参
     * 返回值ResopnseVo<CartVo>
     * */
    @Override
    public ResponseVo<CartVo> add(Integer uid,CartAddForm form) {
        Integer quantity = 1;
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
        //key:cart_1
        //value是购物车数据，挑3个重要字段保存去redis
        //需要引入gson去转换格式toJson
        redisTemplate.opsForValue().set(String.format(CART_REDIS_KEY_TEMPLATE,uid),
                gson.toJson(new Cart(product.getId(),quantity,form.getSelected())));



        return null;
    }
}
