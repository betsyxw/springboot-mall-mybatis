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


    public CartProductVo(Integer productId, Integer quantity, String productName, String productSubtitle, String productMainImage, BigDecimal productPrice, Integer productStatus, BigDecimal productTotalPrice, Integer productStock, Boolean productSelected) {
        this.productId = productId;
        this.quantity = quantity;
        this.productName = productName;
        this.productSubtitle = productSubtitle;
        this.productMainImage = productMainImage;
        this.productPrice = productPrice;
        this.productStatus = productStatus;
        this.productTotalPrice = productTotalPrice;
        this.productStock = productStock;
        this.productSelected = productSelected;
    }
}
