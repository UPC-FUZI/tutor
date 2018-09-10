package com.wf.tutor.controller;

import com.wf.tutor.domain.UserInfo;
import com.wf.tutor.model.AccountLoginRequest;
import com.wf.tutor.model.AccountModifyRequest;
import com.wf.tutor.model.AccountRegisterRequest;
import com.wf.tutor.service.UserAccountService;
import com.wf.tutor.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/account")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;

    @PostMapping(value = "/register")
    public ApiResult<Boolean> register(@RequestBody AccountRegisterRequest request) {
        return userAccountService.register(request);
    }

    @PostMapping(value = "/login")
    public ApiResult<UserInfo> login(@RequestBody AccountLoginRequest request) {
        return userAccountService.login(request);
    }

    @PostMapping(value = "/modify")
    public ApiResult<Boolean> modify(@RequestBody AccountModifyRequest request) {
        return ApiResult.createSuccess(true);
    }
}
