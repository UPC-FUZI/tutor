package com.wf.tutor.controller;

import com.wf.tutor.common.ApiResult;
import com.wf.tutor.domain.Message;
import com.wf.tutor.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/msg")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping(value = "get/{userId}")
    public ApiResult<List<Message>> getMessgeList(@PathVariable("userId") String userId) {
        return ApiResult.createSuccess(messageService.getMessgeList(userId));
    }

    @GetMapping(value = "getHistoryMessage/{fromId}/{toId}")
    public ApiResult<List<Message>> getHistoryMessage(
            @PathVariable("fromId") String fromId, @PathVariable("toId") String toId) {
        return ApiResult.createSuccess(messageService.getHistoryMessage(fromId, toId));

    }
}
