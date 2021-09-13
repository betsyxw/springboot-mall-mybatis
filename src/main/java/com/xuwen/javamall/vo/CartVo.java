package com.xuwen.javamall.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * author:xuwen
 * Created on 2021/9/13
 * 购物车
 */
@Data
public class CartVo {
    private List<CartProductVo> cartProductVoList;

    private Boolean selectedAll;

    private BigDecimal cartTotalPrice;

    private Integer cartTotalQuantity;
}
