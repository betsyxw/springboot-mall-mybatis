package com.xuwen.javamall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * author:xuwen
 * Created on 2021/9/4
 */
@Data
public class UserLoginForm {
//数据类不要耦合！！！

    @NotBlank
    private String username;

    @NotBlank
    private String password;


}
