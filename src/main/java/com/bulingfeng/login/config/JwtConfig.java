package com.bulingfeng.login.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/31
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private String secret;
    private Long expiration;
    private String header;



}
