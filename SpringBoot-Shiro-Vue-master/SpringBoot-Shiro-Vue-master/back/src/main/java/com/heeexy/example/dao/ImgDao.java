package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heeexy.example.entity.Img;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ImgDao extends BaseMapper<Img> {

    @Select("select distinct type from img where category = '壁纸'")
    List<String> getWallTypeList();

    @Select("select distinct type from img where category = '表情包'")
    List<String> getLookTypeList();

}
