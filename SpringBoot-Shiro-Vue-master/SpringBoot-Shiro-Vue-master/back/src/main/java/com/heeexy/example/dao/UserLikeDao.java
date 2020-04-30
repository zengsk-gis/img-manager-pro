package com.heeexy.example.dao;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.entity.Album;
import com.heeexy.example.entity.UserLike;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserLikeDao extends BaseMapper<UserLike> {

    @Select("select * from user_like ul" +
            " join img where ul.img_id = img.id and ul.user_id = #{userId}")
    List<JSONObject> getLoveList(@Param("userId") Long userId);
}
