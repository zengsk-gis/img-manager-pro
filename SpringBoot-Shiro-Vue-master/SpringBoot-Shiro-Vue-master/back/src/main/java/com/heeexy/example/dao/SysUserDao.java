package com.heeexy.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserDao extends BaseMapper<SysUser> {
}
