package com.xuwen.javamall.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * author:xuwen
 * Created on 2021/9/4
 */
@Data
public class UserForm {
    //@NotEmpt用于集合
    //@NotBlank用于String ，判断空格
    //@NotNull

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotBlank
    private String email;

}
