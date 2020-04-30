package com.heeexy.example.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.dao.LookPicDao;
import com.heeexy.example.entity.LookPic;
import com.heeexy.example.entity.WallPic;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LookPicService extends BaseService<LookPicDao, LookPic> {

    public PageInfo getList(String type, String createUserId, String name,
                            Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        QueryWrapper<LookPic> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(type)) {
            queryWrapper.eq("type", type);
        }

        if (Strings.isNotEmpty(createUserId)) {
            queryWrapper.eq("create_user_id", createUserId);
        }

        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", "%" + name + "%");
        }
        List<LookPic> lookPicList = this.list(queryWrapper);
        PageInfo pageInfo = new PageInfo(lookPicList);
        return pageInfo;
    }
}
