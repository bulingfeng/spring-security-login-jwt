package com.bulingfeng.login.filter;

import com.bulingfeng.login.service.UserServie;
import com.bulingfeng.login.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/30
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    public static String tokenHead = "Bearer ";
    public static String tokenHeader = "Authorization";

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 对token进行校验
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader(tokenHeader);
        if (authHeader != null && authHeader.startsWith(tokenHead)) {
            // 1、获取到request的header中的token
            // 2、由于token一本存于redis服务器，这里假设通过token去内存数据库中能查询到相关数据。
            final String authToken = authHeader.substring(tokenHead.length());
                // 如果把token放到redis，并且token是作为key，username是作为value。那么理论上无需进行token的校验，
                // 因为既然redis中存在这个token就代表这个token是合法的。
                //  但是如果redis中的数据token被攻击，并且攻击者使用的是redis已经存在的token，那么则上述则有漏洞，则还需要进行校验。
                // 这里假设从redis拿到的用户为root
                String username = "root";
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (!jwtUtil.isTokenExpired(authToken) && jwtUtil.validateToken(authToken,userDetails)){
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                                httpServletRequest));
                        logger.info("authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }

                }
            }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
