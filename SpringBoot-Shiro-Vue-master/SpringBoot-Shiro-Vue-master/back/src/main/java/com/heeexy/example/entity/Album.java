package com.heeexy.example.entity;

import lombok.Data;

@Data
public class Album extends BasePlusEntity{
    private String name;

    private Long createUserId;

}
