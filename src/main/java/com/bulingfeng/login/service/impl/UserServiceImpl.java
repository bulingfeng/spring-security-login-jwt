package com.bulingfeng.login.service.impl;

import com.bulingfeng.login.dao.SysUserDao;
import com.bulingfeng.login.entity.Auth;
import com.bulingfeng.login.entity.SysUserPo;
import com.bulingfeng.login.service.UserServie;
import com.bulingfeng.login.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/31
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServie {

    private SysUserDao sysUserDao;

    private PasswordEncoder passwordEncoder;

    private JwtUtil jwtUtil;

    @Override
    public Auth login(String username, String password) throws Exception {
        SysUserPo sysUserPo=sysUserDao.selectUserByName2(username);
        String encoderPassword=passwordEncoder.encode(password);
        if (sysUserPo.getPassword().equals(encoderPassword)){
            Auth auth=new Auth();
            auth.setAccessToken(jwtUtil.createJwtToken(sysUserPo));
            return auth;
        }
        throw new Exception("账号密码不正确");
    }
}
