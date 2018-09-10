package com.wf.tutor.model;

import lombok.Data;

@Data
public class AccountLoginRequest {
    private String telephone;
    private String password;
}
