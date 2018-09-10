package com.wf.tutor.dao;

import com.wf.tutor.domain.UserAccount;
import com.wf.tutor.model.AccountLoginRequest;
import com.wf.tutor.model.AccountModifyRequest;
import com.wf.tutor.model.AccountRegisterRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserAccountMapper {

    int insert(AccountRegisterRequest record);

    UserAccount getUserAccount(AccountLoginRequest request);

    int getUserByTelephone(AccountRegisterRequest request);

    int updateByUserId(AccountModifyRequest request);
}