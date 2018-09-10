package com.wf.tutor.service;

import com.wf.tutor.common.ApiResult;
import com.wf.tutor.common.ErrorCodeEnum;
import com.wf.tutor.common.LoggerBase;
import com.wf.tutor.dao.UserAccountMapper;
import com.wf.tutor.dao.UserInfoMapper;
import com.wf.tutor.domain.UserAccount;
import com.wf.tutor.domain.UserInfo;
import com.wf.tutor.model.AccountLoginRequest;
import com.wf.tutor.model.AccountModifyRequest;
import com.wf.tutor.model.AccountRegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Service
public class UserAccountService extends LoggerBase {
    @Autowired
    UserAccountMapper userAccountMapper;
    @Autowired
    UserInfoMapper userInfoMapper;

    @Transactional
    public ApiResult<Boolean> register(AccountRegisterRequest request) {
        int count = userAccountMapper.getUserByTelephone(request);
        if (count >= 1) {
            return ApiResult.createError(ErrorCodeEnum.ALREADY_REGISTERED);
        }
        request.setUserId(createUserId());
        request.setPassword(encryptPassword(request.getPassword()));
        int result = userAccountMapper.insert(request);
        return result >= 1 ? ApiResult.createSuccess(true) : ApiResult.createError(ErrorCodeEnum.REGISTERED_FAIL);
    }

    //TODO 登录成功后，查询用户基本信息
    public ApiResult<UserInfo> login(AccountLoginRequest request) {
        request.setPassword(encryptPassword(request.getPassword()));
        UserAccount userAccount = userAccountMapper.getUserAccount(request);
        if (userAccount == null || !userAccount.isEnabled()) {
            return ApiResult.createError(ErrorCodeEnum.Login_FAIL);
        }
        UserInfo userInfo = userInfoMapper.getUserInfoByUserId(userAccount.getUserId());
        return userInfo == null ? ApiResult.createError(ErrorCodeEnum.FAIL) : ApiResult.createSuccess(userInfo);
    }

    public ApiResult<Boolean> modify(AccountModifyRequest request) {
        int result = userAccountMapper.updateByUserId(request);
        return result >= 1 ? ApiResult.createSuccess(true) : ApiResult.createError(ErrorCodeEnum.FAIL);
    }

    private String encryptPassword(String password) {
        String encrypt = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(password.getBytes());
            byte[] b = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            encrypt = buf.toString();

        } catch (NoSuchAlgorithmException e) {
            getLogger().error("生成加密密码{}", e);
        }
        return encrypt;
    }

    private String createUserId() {
        return UUID.randomUUID().toString();
    }
}
