package com.xuwen.javamall.form;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * author:xuwen
 * Created on 2021/10/21
 */
@Data
public class OrderCreateForm {

    @NotNull
    private Integer shippingId;


}
