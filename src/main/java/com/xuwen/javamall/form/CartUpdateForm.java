package com.xuwen.javamall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * author:xuwen
 * Created on 2021/9/13
 * 添加购物车
 */

@Data
public class CartUpdateForm {

    private Integer quantity;

    private Boolean selected;
}
