package com.xuwen.javamall.vo;

import lombok.Data;

import java.util.List;

/**
 * author:xuwen
 * Created on 2021/9/7
 */
//查询后，返回的数据类型
@Data
public class CategoryVo {
    private Integer id;

    private Integer parentId;

    private String name;

    private Integer sortOrder;

    private List<CategoryVo> subCategories;


}
