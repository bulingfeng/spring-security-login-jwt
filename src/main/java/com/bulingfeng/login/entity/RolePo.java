package com.bulingfeng.login.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * @Author:bulingfeng
 * @Date: 2021/7/26
 */
public class RolePo implements GrantedAuthority {
    private String role="ROLE_ADMIN";
    @Override
    public String getAuthority() {
        return this.getRole();
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
