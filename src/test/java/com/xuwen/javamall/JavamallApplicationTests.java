package com.xuwen.javamall;


import com.xuwen.javamall.dao.CategoryMapper;
import com.xuwen.javamall.pojo.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JavamallApplicationTests {

    //初始化需要有个方法，否则build报错
    @Test
    public void load(){
        System.out.println("loading!!!test");
    }


}
