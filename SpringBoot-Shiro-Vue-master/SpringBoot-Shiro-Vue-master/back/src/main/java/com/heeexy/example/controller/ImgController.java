package com.heeexy.example.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.heeexy.example.entity.Img;
import com.heeexy.example.service.ImgService;
import com.heeexy.example.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("img")
@RestController
public class ImgController {

    @Autowired
    private ImgService imgService;

    @GetMapping("list")
    private JSONObject list(String category, String createUserId, String status, String name,
                            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "0") Integer pageNum) {
        return CommonUtil.successJson(imgService.getImgList(category, createUserId, status,
                name, null, pageSize, pageNum));
    }


    @PostMapping("check")
    private JSONObject check(String id, String status) {
        return CommonUtil.successJson(imgService.checkStatus(id, status));
    }

    @GetMapping("{id}")
    private JSONObject getOne(@PathVariable(value = "id") Long id) {
        return CommonUtil.successJson(imgService.getById(id));
    }

    @GetMapping("query")
    private JSONObject query(String name, String category,
                             @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "0") Integer pageNum) {
        return CommonUtil.successJson(imgService.queryImgs(name, category, pageSize, pageNum));
    }

    @DeleteMapping("{id}")
    private JSONObject delete(@PathVariable(value = "id") Long id) {
        return CommonUtil.successJson(imgService.removeById(id));
    }

    @PutMapping()
    private JSONObject update(@RequestBody Img img) {
        return CommonUtil.successJson(imgService.updateById(img));
    }
    @PostMapping("upload")
    private JSONObject upload(Img img) throws Exception {
        imgService.add(img);
        return CommonUtil.successJson();
    }


}
