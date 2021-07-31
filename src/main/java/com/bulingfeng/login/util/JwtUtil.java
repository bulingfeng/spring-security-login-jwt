package com.bulingfeng.login.util;

import com.bulingfeng.login.config.JwtConfig;
import com.bulingfeng.login.entity.SysUserPo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/29
 * 关于JWT的工具类
 */
@Component
public class JwtUtil {

    @Autowired
    private JwtConfig jwtConfig;

    public static final Key key= Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String createJwtToken(UserDetails userDetails){
        long now=System.currentTimeMillis();
        return Jwts.builder()
                .setId("root")
                .claim("authoties",
                        userDetails.getAuthorities().stream().map(a->a.getAuthority()).collect(Collectors.toList()))
                .setSubject(userDetails.getUsername())
                // 签发时间
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + jwtConfig.getExpiration()))
                .signWith(key,SignatureAlgorithm.HS512)
                .compact();

    }


    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token).get();
            Long expiration = claims.getExpiration().getTime();
            return expiration < System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    private Optional<Claims> getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return Optional.ofNullable(claims);
    }


    /**
     * 校验token是否有效
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        SysUserPo user = (SysUserPo) userDetails;
        Optional<Claims> optionalClaims=getClaimsFromToken(token);
        String username =null;
        if (optionalClaims.isPresent()){
           username = optionalClaims.get().getSubject();
           return (username!=null && username.equals(user.getUsername()) && !isTokenExpired(token));
        }
        return false;
    }





}
