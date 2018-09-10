package com.wf.tutor.dao;

import com.wf.tutor.domain.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserInfoMapper {
    int insert(UserInfo record);

    UserInfo getUserInfoByUserId(String userId);

    @Select("select real_name from user_info where user_id = #{userId}")
    String getRealNameByUserId(@Param("userId") String userId);

    @Select("select nick_name from user_info where user_id = #{userId}")
    String getNickNameByUserId(@Param("userId") String userId);
}