package com.heeexy.example.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.heeexy.example.dao.ImgDao;
import com.heeexy.example.entity.Img;
import com.heeexy.example.util.constants.Constants;
import org.apache.logging.log4j.util.Strings;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.util.List;

@Service
public class ImgService extends BaseService<ImgDao, Img>{

    public PageInfo getImgList(String category, String createUserId, String status, String name, String type,
                                Integer pageSize, Integer pageNum) {
        QueryWrapper<Img> queryWrapper = new QueryWrapper<>();
        if (Strings.isNotEmpty(category)) {
            queryWrapper.eq("category", category);
        }

        if (Strings.isNotEmpty(status)) {
            queryWrapper.eq("status", status);
        }

        if (Strings.isNotEmpty(createUserId)) {
            queryWrapper.eq("create_user_id", createUserId);
        }
        if (Strings.isNotEmpty(type)) {
            queryWrapper.eq("type", type);
        }

        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", "%" + name + "%");
        }

        PageHelper.startPage(pageNum,pageSize);
        List<Img> imgList = this.list(queryWrapper);
        PageInfo pageInfo = new PageInfo(imgList);
        return pageInfo;
    }

    public boolean checkStatus(String imgId, String status) {
        Img img= new Img();
        img.setId(Long.parseLong(imgId.trim()));
        img.setStatus(status);
        return this.updateById(img);
    }

    public PageInfo queryImgs(String name, String category, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<Img> queryWrapper = new QueryWrapper<>();

        if (Strings.isNotEmpty(name)) {
            queryWrapper.like("name", "%" + name + "%");
        }

        if (Strings.isNotEmpty(category)) {
            queryWrapper.eq("category", category);
        }

        List<Img> imgList = this.list(queryWrapper);
        PageInfo pageInfo = new PageInfo(imgList);
        return pageInfo;
    }

    public void add(Img img) throws Exception{
        Session session = SecurityUtils.getSubject().getSession();
        JSONObject userInfo = (JSONObject) session.getAttribute(Constants.SESSION_USER_INFO);
        String userId = userInfo.getString("userId");
        String image = new BASE64Encoder().encode(img.getFile().getBytes());
        image = "data:image/jgp;base64," + image;
        img.setImg(image);
        img.setStatus("上线中");
        img.setCreateUserId(Long.valueOf(userId));
        this.save(img);
    }

    /**
     *  0 bizhi  1 表情包
     * @param type
     * @return
     */
    public List<String> getTypeList(Long type) {
        if (type == 0) {
            return this.baseMapper.getWallTypeList();
        } else {
            return this.baseMapper.getLookTypeList();
        }
    }

    public List<Img> getHotList() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("category", "壁纸");
        queryWrapper.gt("hot", 3);
        List<Img> imgList = this.list(queryWrapper);
        if (imgList.size()> 3) {
            return imgList.subList(0, 4);
        } else {
            return imgList;
        }
    }

}
