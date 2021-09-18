package com.xuwen.javamall.service.Impl;

import com.google.gson.Gson;
import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.enums.ProductStatusEnum;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.form.CartAddForm;
import com.xuwen.javamall.form.CartUpdateForm;
import com.xuwen.javamall.pojo.Cart;
import com.xuwen.javamall.pojo.Product;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.vo.CartProductVo;
import com.xuwen.javamall.vo.CartVo;
import com.xuwen.javamall.vo.ResponseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.xml.ws.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
     * add接口，实现类
     *
     * */
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
        /**
         * redis,里面有个类似java中map的结构Hash
         * List找数据是依靠，遍历，查找，性能低
         * Map查找数据是依靠，K，V，性能高
         * */
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        Cart cart = new Cart();
        String value = opsForHash.get(redisKey, String.valueOf(product.getId()));

        if(StringUtils.isEmpty(value)){
            //空，redis没有商品，需要新增
            cart = new Cart(product.getId(), quantity, form.getSelected());

        }else {
            //有，则数量需要追加+1
            cart = gson.fromJson(value, Cart.class);
            cart.setQuantity(cart.getQuantity()+quantity);

        }
        opsForHash.put(redisKey,
                String.valueOf(product.getId()),
                gson.toJson(cart));



        return list(uid);
    }

    /**
     * list接口，实现类,字段
     *返回值：ResponseVo<CartVo>
     * */
    @Override
    public ResponseVo<CartVo> list(Integer uid) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);

        Map<String, String> entries = opsForHash.entries(redisKey);

        boolean selectAll = true;
        Integer cartTotalQuantity = 0;
        BigDecimal cartTotalPrice = BigDecimal.ZERO;
        CartVo cartVo = new CartVo();
        List<CartProductVo> cartProductVoList = new ArrayList<>();

        for (Map.Entry<String, String> entry : entries.entrySet()) {
            Integer productId = Integer.valueOf(entry.getKey());
            Cart cart = gson.fromJson(entry.getValue(), Cart.class);
            //拿到productId，开始查数据库
            //ToDo,使用mysql 的in
            Product product = productMapper.selectByPrimaryKey(productId);
            if(product != null){
                CartProductVo cartProductVo = new CartProductVo(productId,
                        cart.getQuantity(),
                        product.getName(),
                        product.getSubtitle(),
                        product.getMainImage(),
                        product.getPrice(),
                        product.getStatus(),
                        product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity())),
                        product.getStock(),
                        cart.getProductSelected()
                        );
                cartProductVoList.add(cartProductVo);
                if(!cart.getProductSelected()){
                    selectAll = false;
                }
                //只计算选中
                if(cart.getProductSelected()){
                    cartTotalPrice = cartTotalPrice.add(cartProductVo.getProductTotalPrice());
                }
            }
            cartTotalQuantity += cart.getQuantity();

        }
        //返回给前端的字段
        //有一个没选中，就不是全选
        cartVo.setSelectedAll(selectAll);
        cartVo.setCartTotalQuantity(cartTotalQuantity);
        cartVo.setCartTotalPrice(cartTotalPrice);
        cartVo.setCartProductVoList(cartProductVoList);
        return ResponseVo.success(cartVo);
    }


    /**
     * Update更新购物车
     * 返回值：ResponseVo<CartVo>
     *
     * */
    @Override
    public ResponseVo<CartVo> update(Integer uid, Integer productId, CartUpdateForm form) {
        //要更新，先查redis
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));

        if(StringUtils.isEmpty(value)){
            //空，redis没有商品，数据出错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);

        }
            //有，修改
        Cart cart = gson.fromJson(value, Cart.class);
        if(form.getQuantity()!=null && form.getQuantity()>=0){
            cart.setQuantity(form.getQuantity());
        }
        if(form.getSelected()!=null){
            cart.setProductSelected(form.getSelected());
        }

        opsForHash.put(redisKey,String.valueOf(productId),gson.toJson(cart));

        return list(uid);
    }

    /**
     * 删除delete
     *
     * **/
    @Override
    public ResponseVo<CartVo> delete(Integer uid, Integer productId) {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        String redisKey = String.format(CART_REDIS_KEY_TEMPLATE,uid);
        String value = opsForHash.get(redisKey, String.valueOf(productId));
        if(StringUtils.isEmpty(value)){
            //空，redis没有商品，数据出错
            return ResponseVo.error(ResponseEnum.CART_PRODUCT_NOT_EXIST);

        }
        opsForHash.delete(redisKey,String.valueOf(productId));
        return list(uid);
    }
}
