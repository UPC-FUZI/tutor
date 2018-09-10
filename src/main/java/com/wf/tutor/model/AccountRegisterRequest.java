package com.wf.tutor.model;

import lombok.Data;

@Data
public class AccountRegisterRequest {
    private String userId;
    private String telephone;
    private String password;
}
