package com.heeexy.example.entity;

import lombok.Data;

@Data
public class SysUser extends BasePlusEntity{

    private String username;

    private String nickname;

    private String password;

    private Integer deleteStatus;

    private Long roleId;

}
