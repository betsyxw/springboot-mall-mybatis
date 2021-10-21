package com.xuwen.javamall.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xuwen.javamall.dao.OrderItemMapper;
import com.xuwen.javamall.dao.OrderMapper;
import com.xuwen.javamall.dao.ProductMapper;
import com.xuwen.javamall.dao.ShippingMapper;
import com.xuwen.javamall.enums.OrderStatusEnum;
import com.xuwen.javamall.enums.PaymentTypeEnum;
import com.xuwen.javamall.enums.ProductStatusEnum;
import com.xuwen.javamall.enums.ResponseEnum;
import com.xuwen.javamall.pojo.*;
import com.xuwen.javamall.service.ICartService;
import com.xuwen.javamall.service.IOrderService;
import com.xuwen.javamall.vo.OrderItemVo;
import com.xuwen.javamall.vo.OrderVo;
import com.xuwen.javamall.vo.ResponseVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;


    @Override
    @Transactional//开启事务,innodb支持，Myisam不支持事务
    public ResponseVo<OrderVo> create(Integer uid, Integer shippingId) {
        /**
         * 收货地址校验，从数据库中shippingMapper查
         * */
        //shippingMapper
        Shipping shipping = shippingMapper.selectByUidAndShippingId(uid, shippingId);
        if(shipping == null){
            return ResponseVo.error(ResponseEnum.SHIPPING_NOT_EXIST);
        }
        /**
         * uid->获取购物车+校验(是否有商品，库存),stream选中商品
         * 选中商品，用steamAPI
         * */
        List<Cart> cartList = cartService.listForCart(uid).stream()
                .filter(Cart::getProductSelected)
                .collect(Collectors.toList());
        //list是否是空的
        if(CollectionUtils.isEmpty(cartList)){
            return ResponseVo.error(ResponseEnum.CART_SELECTED_IS_EMPTY);
        }
        //获取CartList里的productIds,set=id不可能重复
        //productList是从数据库中获取到的数据！
        Set<Integer> productIdSet = cartList.stream().map(Cart::getProductId).collect(Collectors.toSet());
        List<Product> productList = productMapper.selectByProductIdSet(productIdSet);
        //productList->改造map,自己造了map，数据库信息map
        Map<Integer,Product> map = productList.stream()
                .collect(Collectors.toMap(Product::getId,product->product));

        //数据库查库存
        List<OrderItem> orderItemList = new ArrayList<>();
        Long orderNo = generateOrderNo();
        for (Cart cart : cartList) {
            //根据productId查询数据库
            Product product = map.get(cart.getProductId());
            //是否有该商品,对比数据库
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

            //减库存
            product.setStock(product.getStock()-cart.getQuantity());
            //updateByPrimaryKeySelective不需要每个字段都传
            int row = productMapper.updateByPrimaryKeySelective(product);
            if(row <=0){
                return ResponseVo.error(ResponseEnum.ERROR);
            }

        }
        /**
         * 计算总价
         * 生成订单，入库，2个表，order，和order_item,同时写入事务！
         * */
        Order order = buildOrder(uid, orderNo, shippingId, orderItemList);
        //事务控制
        int rowForOrder = orderMapper.insertSelective(order);
        if(rowForOrder<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }

        int rowForOrderItem = orderItemMapper.batchInsert(orderItemList);
        if(rowForOrderItem<=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }



        //更新购物车,操作redis
        //redis的事务是打包命令，单线程，无法回滚
        for (Cart cart : cartList) {
            cartService.delete(uid,cart.getProductId());
        }

        //构造orderVo对象,返回给前端
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    //PageInfo//订单列表
    @Override
    public ResponseVo<PageInfo> list(Integer uid, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        //需要另外2个入参，1orderList，2收货地址
        List<Order> orderList = orderMapper.selectByUid(uid);
        Set<Long> orderNumSet = orderList.stream().map(Order::getOrderNo).collect(Collectors.toSet());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNumSet);
        //orderItemList->Map的结构
        Map<Long,List<OrderItem>> orderItemMap= orderItemList.stream().collect(Collectors.groupingBy(OrderItem::getOrderNo));
        Set<Integer> shippingIdSet = orderList.stream().map(Order::getShippingId).collect(Collectors.toSet());
        List<Shipping> shippingList = shippingMapper.selectByIdSet(shippingIdSet);
        Map<Integer,Shipping> shippingMap = shippingList.stream().collect(Collectors.toMap(Shipping::getId,shipping->shipping));

        //构造Vo对象
        List<OrderVo> orderVoList = new ArrayList<>();
        for(Order order:orderList){
            //buildOrderVo下方已经构造好了，传入的参数需要重新筛选
            OrderVo orderVo = buildOrderVo(order, orderItemMap.get(order.getOrderNo()), shippingMap.get(order.getShippingId()));
            orderVoList.add(orderVo);
        }

        //返回信息pageInfo
        PageInfo pageInfo = new PageInfo(orderList);
        pageInfo.setList(orderVoList);
        return ResponseVo.success(pageInfo);
    }

    //订单详情detail
    @Override
    public ResponseVo<OrderVo> detail(Integer uid, Long orderNo) {
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order ==null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        Set<Long> orderNoSet = new HashSet();
        orderNoSet.add(order.getOrderNo());
        List<OrderItem> orderItemList = orderItemMapper.selectByOrderNoSet(orderNoSet);
        Shipping shipping = shippingMapper.selectByPrimaryKey(order.getShippingId());
        OrderVo orderVo = buildOrderVo(order, orderItemList, shipping);
        return ResponseVo.success(orderVo);
    }

    //取消订单，校验订单是不是属于这个用户
    @Override
    public ResponseVo cancel(Integer uid, Long orderNo) {
        //校验订单是不是属于这个用户
        Order order = orderMapper.selectByOrderNo(orderNo);
        if(order ==null || !order.getUserId().equals(uid)){
            return ResponseVo.error(ResponseEnum.ORDER_NOT_EXIST);
        }
        //未付款才可以取消，
        if(!order.getStatus().equals(OrderStatusEnum.NO_PAY.getCode())){
            return ResponseVo.error(ResponseEnum.ORDER_STATUS_ERROR);
        }
        //取消订单，写入时间戳
        order.setStatus(OrderStatusEnum.CANCELED.getCode());
        order.setCloseTime(new Date());
        int row = orderMapper.updateByPrimaryKeySelective(order);
        if(row <=0){
            return ResponseVo.error(ResponseEnum.ERROR);
        }
        return ResponseVo.success();
    }

    //构造返回给前端的Vo对象
    private OrderVo buildOrderVo(Order order,List<OrderItem> orderItemList,Shipping shipping) {
        OrderVo orderVo = new OrderVo();
        BeanUtils.copyProperties(order,orderVo);

        List<OrderItemVo> orderItemVoList = orderItemList.stream().map(e -> {
            OrderItemVo orderItemVo = new OrderItemVo();
            BeanUtils.copyProperties(e, orderItemVo);
            return orderItemVo;
        }).collect(Collectors.toList());
        orderVo.setOrderItemVoList(orderItemVoList);
        if(shipping != null){
            orderVo.setShippingId(shipping.getId());
            orderVo.setShippingVo(shipping);
        }

        return orderVo;
    }

    private Order buildOrder(Integer uid, Long orderNo, Integer shippingId, List<OrderItem> orderItemList) {
        //太长了，外面计算好，再传入
        BigDecimal payment = orderItemList.stream().map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        //返回值是Order没有自己new一个
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
        //系统时间+3位int随机数
        return System.currentTimeMillis()+new Random().nextInt(999);
    }

    private OrderItem buildOrderItem(Integer uid,Long orderNo,Integer quantity,Product product) {
        //返回值是OrderItem没有自己new一个
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
