package com.bulingfeng.login.service;


import com.bulingfeng.login.entity.Auth;
import com.bulingfeng.login.entity.SysUserPo;

/**
 * @Author:bulingfeng
 * @Date: 2020-06-13
 */
public interface UserServie {

    Auth login(String username,String password) throws Exception;
}
