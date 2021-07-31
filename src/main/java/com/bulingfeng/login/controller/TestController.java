package com.bulingfeng.login.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author:bulingfeng
 * @Date: 2020-08-21
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public String testApi(){
        return "success";
    }

}
