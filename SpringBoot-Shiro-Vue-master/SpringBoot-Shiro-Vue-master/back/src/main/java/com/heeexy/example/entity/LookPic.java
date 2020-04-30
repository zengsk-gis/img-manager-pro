package com.heeexy.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class LookPic extends BasePlusEntity{

    private String lookPic;

    private String type;

    private Integer createUserId;

    private String name;

    private String des;

    @TableField(exist = false)
    private MultipartFile file;

}
