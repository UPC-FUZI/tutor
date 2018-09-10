package com.wf.tutor.domain;

import lombok.Data;

import java.util.Date;
@Data
public class UserInfo {
    private Integer id;

    private String userId;

    private String nickName;

    private String realName;

    private Integer cardId;

    private String city;

    private String address;

    private String school;

    private String headPicture;

    private Integer type;

    private Date createTime;

    private Date updateTime;
}