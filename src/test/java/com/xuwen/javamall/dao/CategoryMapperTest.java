package com.xuwen.javamall.dao;

import com.xuwen.javamall.JavamallApplicationTests;
import com.xuwen.javamall.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


//@RunWith(SpringRunner.class)
//@SpringBootTest
//头上注解不加，就是报错，要么就继承Test爸爸类，JavamallApplicationTests。
public class CategoryMapperTest extends JavamallApplicationTests {
    @Autowired
    private CategoryMapper categoryMapper;

    @Test
    public void findById() {
        Category restfindById = categoryMapper.findById(100001);
        System.out.println(restfindById);
        System.out.println(restfindById.toString());
    }

    @Test
    public void queryById() {
        Category queryById = categoryMapper.queryById(100002);
        System.out.println(queryById.toString());
    }
}