package com.xuwen.javamall;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.xuwen.javamall.dao")
public class JavamallApplication {

    public static void main(String[] args) {

        SpringApplication.run(JavamallApplication.class, args);
    }

}
