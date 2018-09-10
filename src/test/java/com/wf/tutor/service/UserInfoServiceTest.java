package com.wf.tutor.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserInfoServiceTest {
    @Autowired
    UserInfoService userInfoService;
    @Test
    public void getNickNameByUserId() throws Exception {
        System.out.println(userInfoService.getUserByUserId("1532934949981ca4ea3a-302c-45bd-8443-2b076e549d07"));
    }
}
