package com.xuwen.javamall.service.Impl;

import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.dao.ShippingMapper;
import com.xuwen.javamall.enums.OrderStatusEnum;
import com.xuwen.javamall.enums.PaymentTypeEnum;
import com.xuwen.javamall.enums.ProductStatusEnum;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.pojo.*;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.service.IOrderService;
import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * author:xuwen
 * Created on 2021/9/23
 */
@Service
@Slf4j
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private ShippingMapper shippingMapper;

    @Autowired
    private ICartService cartService;

    @Autowired
    private ProductMapper productMapper;


    @Override
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        /**
         * 收货地址校验，从数据库中取得
         * */
        //shipping,dao中数据，注入
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if(shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        /**
         * 获取购物车+校验(是否有商品，库存)
         * 选中商品，用steamAPI
         * */
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //获取CartList里的productIds,set=id不可能重复
        //productList是从数据库中获取到的数据！
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        //自己造了map，传入我需要的信息
        Map<Integer,Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId,product->product));


        //数据库查库存
        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            Product product = map.get(cart.getProductId());
            //是否有该商品
            if(product == null){
                return ResponseVo.error(ResponseEnum.PRODUCT_NOT_EXIST,"商品不存在.productId="+cart.getProductId());
            }
            //商品上下架状态
            if(!ProductStatusEnum.ON_SALE.getCode().equals(product.getStatus())){
                return ResponseVo.error(ResponseEnum.PRODUCT_OFF_SALE_OR_DELETE,"商品不是在售状态"+product.getName());
            }
            //商品库存情况
            if(product.getStock()< cart.getQuantity()){
                return ResponseVo.error(ResponseEnum.PRODUCT_STOCK_ERROR,"库存不正确"+product.getName());
            }

            OrderItem orderItem = buildOrderItem(uid, orderNo, cart.getQuantity(), product);
            orderItemList.add(orderItem);
        }
        /**
         * 计算总价
         * 生成订单，入库，2个表，order，和order_item,同时写入事务！
         * */
        buildOrder(uid,orderNo,shippingId,orderItemList);




        //减库存


        //更新购物车


        //构造orderVo对象



        return null;
    }

    private Order buildOrder(Integer uid,
                             Long orderNo,
                             Integer shippingId,
                             List<OrderItem> orderItemList) {
        //太长了，外面计算好，再传入
        BigDecimal payment = orderItemList.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setUserId(uid);
        order.setShippingId(shippingId);
        order.setPayment(payment);
        order.setPaymentType(PaymentTypeEnum.PAY_ONLINE.getCode());
        order.setPostage(0);
        order.setStatus(OrderStatusEnum.NO_PAY.getCode());

        return null;
    }

    /**
     * 企业级：自己搜索分布式唯一ID
     * */
    private Long generateOrderNo() {
        //系统时间+3位随机数
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product) {
        OrderItem item = new OrderItem();
        item.setUserId(uid);
        item.setOrderNo(orderNo);
        item.setProductId(product.getId());
        item.setProductName(product.getName());
        item.setProductImage(product.getMainImage());
        item.setCurrentUnitPrice(product.getPrice());
        item.setQuantity(quantity);
        item.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(quantity)));
        return item;
    }
}
