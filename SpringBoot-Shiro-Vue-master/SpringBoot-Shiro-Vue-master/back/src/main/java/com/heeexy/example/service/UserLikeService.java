package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.heeexy.example.dao.AlbumDao;
import com.heeexy.example.dao.UserLikeDao;
import com.heeexy.example.entity.Album;
import com.heeexy.example.entity.UserLike;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLikeService extends BaseService<UserLikeDao, UserLike> {

    public List<JSONObject> getLoveList(Long userId) {
        return this.baseMapper.getLoveList(userId);
    }
}
