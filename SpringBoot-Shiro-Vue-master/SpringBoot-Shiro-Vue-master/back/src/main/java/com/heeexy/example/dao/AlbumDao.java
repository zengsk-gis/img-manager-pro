package com.heeexy.example.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.heeexy.example.entity.Album;
import com.heeexy.example.entity.WallPic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AlbumDao extends BaseMapper<Album> {

}
