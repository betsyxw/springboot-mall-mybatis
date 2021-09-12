package com.xuwen.javamall.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * author:xuwen
 * Created on 2021/9/12
 */

//返回给前端的字段，自己定制！！
@Data
public class ProductVo {
    private Integer id;

    private Integer categoryId;

    private String name;

    private String subtitle;

    private String mainImage;

    private Integer status;

    private BigDecimal price;
}
