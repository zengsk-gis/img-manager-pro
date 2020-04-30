package com.heeexy.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.xml.ws.Service;

public class BaseService<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

    public T getOneByField(String field, Object value) {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(field, value);
        return this.getOne(queryWrapper);
    }

    public QueryWrapper<T> getQueryWrapper() {
        return new QueryWrapper<>();
    }

}
