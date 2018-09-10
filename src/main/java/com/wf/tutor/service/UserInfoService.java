package com.wf.tutor.service;

import com.wf.tutor.dao.UserInfoMapper;
import com.wf.tutor.domain.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoService {
    @Autowired
    UserInfoMapper userInfoMapper;

    public UserInfo getUserByUserId(String userId) {
        return userInfoMapper.getUserInfoByUserId(userId);
    }

    public boolean saveUser(UserInfo userInfo) {
        int result = userInfoMapper.insert(userInfo);
        return result >= 1;
    }

    /**
     * 先查真实姓名，若没有再查昵称
     */
    public String getUserNameByUserId(String userId) {
        String userName = userInfoMapper.getRealNameByUserId(userId);
        if (userName == null || userName.isEmpty()) {
            return userInfoMapper.getNickNameByUserId(userId);
        }
        return userName;
    }
}
