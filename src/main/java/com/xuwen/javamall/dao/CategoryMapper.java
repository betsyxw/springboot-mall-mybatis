package com.xuwen.javamall.dao;


import com.xuwen.javamall.pojo.Category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


public interface CategoryMapper {

    //mybatis注入写法
    @Select("select * from mall_category where id = #{id}")
    Category findById(@Param("id") Integer id);

    //xml写法
    Category queryById(Integer id);

}
