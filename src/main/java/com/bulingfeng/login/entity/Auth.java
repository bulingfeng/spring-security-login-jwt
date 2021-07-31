package com.bulingfeng.login.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/31
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Auth {
    private String accessToken;
    private String refreshToken;
}
