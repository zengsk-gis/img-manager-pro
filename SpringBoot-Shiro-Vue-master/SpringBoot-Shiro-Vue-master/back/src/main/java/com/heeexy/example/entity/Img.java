package com.heeexy.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class Img extends BasePlusEntity{

    private String img;

    //图片审核状态
    private String status;

    private String type;

    private String category;

    private Long createUserId;

    private String name;

    private Long albumId;

    //图片描述
    private String des;

    //图片是否共享
    private Integer common;

    //是否热门
    private Integer hot;

    @TableField(exist = false)
    private MultipartFile file;

}
