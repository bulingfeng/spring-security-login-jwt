package com.bulingfeng.login.service.impl;

import com.bulingfeng.login.dao.SysUserDao;
import com.bulingfeng.login.entity.SysUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @Author:bulingfeng
 * @Date: 2020-06-13
 */
@Component
@Slf4j
public class CustomUserService implements UserDetailsService {

    @Autowired
    private SysUserDao userDao;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserPo po=userDao.selectUserByName2(username);
        return po;
    }

//    public Auth login(String username, String password) {
//        return userRepo.findOptionalByUsername(username)
//                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
//                .map(user -> new Auth(jwtUtil.createAccessToken(user), jwtUtil.createRefreshToken(user)))
//                .orElseThrow(() -> new AccessDeniedException("用户名密码错误"));
//    }
}
