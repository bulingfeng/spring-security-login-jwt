package com.bulingfeng.login.util;

import com.bulingfeng.login.entity.SysUserPo;
import io.jsonwebtoken.Jwts;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.management.relation.Role;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/29
 */
@SpringBootTest
public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @Before
    public void setUp(){
        jwtUtil=new JwtUtil();
    }


    @Test
    public void parseJwtToken(){
        String username="root";
        SysUserPo userPo=SysUserPo
                .builder()
                .userName("root")
                .build();
        // 创建token
        String token=jwtUtil.createJwtToken(userPo);
        System.out.println("生成的token:"+token);
        // 解析token
        String parseClaims= Jwts.parserBuilder()
                .setSigningKey(JwtUtil.key)
                .build()
                .parseClaimsJws(token)
                .getBody().getSubject();
        assertEquals(true, username.equals(parseClaims));
    }



}
