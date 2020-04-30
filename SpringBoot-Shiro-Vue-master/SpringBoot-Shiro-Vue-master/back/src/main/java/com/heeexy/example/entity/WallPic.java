package com.heeexy.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class WallPic extends BasePlusEntity{

    private String wallPic;

    private String type;

    private Integer createUserId;

    private String name;

    private String des;

    private int hot;

    @TableField(exist = false)
    private MultipartFile file;

}
