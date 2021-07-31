package com.bulingfeng.login;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author:bulingfeng
 * @Date: 2020-08-21
 */
@SpringBootApplication
@MapperScan("com.bulingfeng.login.dao")
public class LoginApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoginApplication.class);
    }
}
