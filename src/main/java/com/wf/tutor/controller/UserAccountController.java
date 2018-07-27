package com.wf.tutor.controller;

import com.wf.tutor.common.ErrorCodeEnum;
import com.wf.tutor.domain.UserInfo;
import com.wf.tutor.model.AccountLoginRequest;
import com.wf.tutor.model.AccountModifyRequest;
import com.wf.tutor.model.AccountRegisterRequest;
import com.wf.tutor.service.UserAccountService;
import com.wf.tutor.common.ApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
public class UserAccountController {
    @Autowired
    UserAccountService userAccountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ApiResult<Boolean> register(@RequestBody AccountRegisterRequest request) {
        return userAccountService.register(request);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<UserInfo> login(@RequestBody AccountLoginRequest request) {
        return userAccountService.login(request);
    }

    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    public ApiResult<Boolean> modify(@RequestBody AccountModifyRequest request) {
        return ApiResult.createSuccess(true);
    }
}
