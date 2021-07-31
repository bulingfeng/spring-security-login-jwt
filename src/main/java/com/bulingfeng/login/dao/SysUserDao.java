package com.bulingfeng.login.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.bulingfeng.login.entity.SysUserPo;

/**
 * @Author:bulingfeng
 * @Date: 2020-06-13
 */
public interface SysUserDao extends BaseMapper<SysUserPo> {
    SysUserPo selectUserByName2(String userName);
}
