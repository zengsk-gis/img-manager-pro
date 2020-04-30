package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heeexy.example.dao.SysUserDao;
import com.heeexy.example.entity.Album;
import com.heeexy.example.entity.Img;
import com.heeexy.example.entity.SysUser;
import com.heeexy.example.entity.UserLike;
import com.heeexy.example.service.*;
import com.heeexy.example.util.CommonUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("app")
public class AppController {
    @Autowired
    private ImgService imgService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserLikeService userLikeService;

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/img/list")
    public JSONObject list(String category, String createUserId, String status, String name, String type,
                            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "0") Integer pageNum) {
        return CommonUtil.successJson(imgService.getImgList(category, createUserId, status,
                name, type, pageSize, pageNum));
    }

    @GetMapping("/album/list")
    public JSONObject albumList(String createUserId) {
        QueryWrapper queryWrapper = null;
        if (Strings.isNotEmpty(createUserId)) {
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("create_user_id", createUserId);
        }
        return CommonUtil.successJson(albumService.list(queryWrapper));
    }

    @GetMapping("/getImgListByAlbum")
    public JSONObject getImgListByAlbum(String albumId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("album_id", albumId);
        return CommonUtil.successJson(imgService.list(queryWrapper));

    }

    @PostMapping("/add/album")
    public JSONObject addAlbum(@RequestBody Album album) {
        albumService.save(album);
        return CommonUtil.successJson();
    }

    @GetMapping("/user")
    public JSONObject getOneUser(Long userId) {
        return CommonUtil.successJson(sysUserService.getById(userId));
    }

    @PutMapping("/user")
    public JSONObject updateUser(@RequestBody SysUser sysUser) {
        sysUserService.updateById(sysUser);
        return CommonUtil.successJson();
    }

    @PostMapping("/add/img")
    public JSONObject addImg(Img img) {
        imgService.save(img);
        return CommonUtil.successJson();
    }

    @PostMapping("/img/like")
    public JSONObject userListImg(@RequestBody UserLike userLike) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id", userLike.getUserId());
        queryWrapper.eq("img_id", userLike.getImgId());
        UserLike db = userLikeService.getOne(queryWrapper);
        if (db == null) {
            userLikeService.save(userLike);
        }
        return CommonUtil.successJson();
    }

    @GetMapping("/img/likeList")
    public JSONObject likeList(Long userId) {
        return CommonUtil.successJson(userLikeService.getLoveList(userId));
    }

    @GetMapping("/typeList")
    public JSONObject getTypeList(Long type) {
        return CommonUtil.successJson(imgService.getTypeList(type));
    }

    @PostMapping("/register")
    public JSONObject register(@RequestBody SysUser sysUser) {
        sysUser.setRoleId(2l);
        sysUserService.save(sysUser);
        return CommonUtil.successJson();
    }

    @GetMapping("/hotList")
    public JSONObject getHotList() {
        return CommonUtil.successJson(imgService.getHotList());
    }

}
