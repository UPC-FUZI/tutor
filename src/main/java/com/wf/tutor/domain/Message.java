package com.wf.tutor.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Message {
    private Integer id;

    private String fromId;

    private String fromName;

    private String toId;

    private String toName;

    private String msg;

    private LocalDateTime createTime;

    private String time;

    private Integer status;
}