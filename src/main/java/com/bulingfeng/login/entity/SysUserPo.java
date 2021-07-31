package com.bulingfeng.login.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author:bulingfeng
 * @Date: 2020-06-13
 */
@TableName("sys_user")
@Data
@Builder
public class SysUserPo implements UserDetails {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField(value  ="user_name")
    private String userName;

    @TableField(value = "password")
    private String password;

    @TableField(value = "role")
    private String role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> auths = new ArrayList<>();
//        auths.add(this::getUsername);
//        auths.add(this::getRole);
        RolePo role=new RolePo();
        auths.add(role);
        return auths;
    }

    @Override
    public String getUsername() {
        System.out.println(this.userName);
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
