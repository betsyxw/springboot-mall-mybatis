package com.xuwen.javamall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/13
 */

@Data
public class CartProductVo {

    private Integer productId;

    /**
     * 购买的数量
     * */
    private Integer quantity;

    private String productName;

    private String productSubtitle;

    private String productMainImage;

    private BigDecimal productPrice;

    private Integer productStatus;

    /**
     * 商品总价=productPrice*quantity
     * */
    private BigDecimal productTotalPrice;

    private Integer productStock;

    private Boolean productSelected;



}
