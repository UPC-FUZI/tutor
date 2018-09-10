package com.wf.tutor.controller;

import com.wf.tutor.dao.UserInfoMapper;
import com.wf.tutor.domain.UserInfo;
import com.wf.tutor.common.ApiResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserInfoController {
    @Autowired
    UserInfoMapper userInfoMapper;

    @PostMapping(value = "/save")
    public ApiResult<Boolean> saveUser(@RequestBody UserInfo userInfo) throws Exception{
        if (true){
            throw new Exception("测试");
        }
        userInfoMapper.insert(userInfo);
        return ApiResult.createSuccess(true);
    }

    @GetMapping (value = "/getUserByUserId")
    public ApiResult<UserInfo> getUserByUserId(@Param("userId") String userId) {
        return ApiResult.createSuccess(userInfoMapper.getUserInfoByUserId(userId));
    }
}
