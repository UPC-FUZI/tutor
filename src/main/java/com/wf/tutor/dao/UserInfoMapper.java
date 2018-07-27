package com.wf.tutor.dao;

import com.wf.tutor.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserInfoMapper {
    int insert(UserInfo record);

    UserInfo getUserInfoByUserId(String userId);
}