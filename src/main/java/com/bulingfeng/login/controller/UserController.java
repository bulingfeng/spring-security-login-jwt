package com.bulingfeng.login.controller;

import com.bulingfeng.login.entity.Auth;
import com.bulingfeng.login.entity.dto.LoginDto;
import com.bulingfeng.login.service.UserServie;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/26
 */
@RequestMapping("/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private UserServie userServie;

    @GetMapping("/get")
    public String getUser(){
        return "bulingfeng";
    }

    @PostMapping("/token")
    public Auth login(@RequestBody LoginDto loginDTO) throws Exception {
        return userServie.login(loginDTO.getUsername(), loginDTO.getPassword());
    }
}
