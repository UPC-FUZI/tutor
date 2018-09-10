package com.wf.tutor.service;

import com.wf.tutor.dao.MessageMapper;
import com.wf.tutor.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserInfoService userInfoService;
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yy-MM-dd");
    private LocalDateTime beforeDawn = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);

    //查询接收的消息
    public List<Message> getMessgeList(String userId) {
        List<Message> messageList = messageMapper.selectByToId(userId);
        if (messageList == null || messageList.isEmpty()) {
            return new ArrayList<>();
        }
        for (Message message : messageList) {
            message.setFromName(userInfoService.getUserNameByUserId(message.getFromId()));
            String content = message.getMsg();
            if (content.length() > 12) {
                message.setMsg(content.substring(0, 12) + "...");
            }
            if (message.getCreateTime().isAfter(beforeDawn)) {
                message.setTime(message.getCreateTime().format(timeFormatter));
            } else {
                message.setTime(message.getCreateTime().format(dateFormatter));
            }
        }
        return messageList;
    }

    //查询和某人的聊天记录
    public List<Message> getHistoryMessage(String fromId, String toId) {
        List<Message> messageList = messageMapper.getHistoryMessage(fromId, toId);
        Collections.sort(messageList, new Comparator<Message>() {
            @Override
            public int compare(Message o1, Message o2) {
                return o1.getId() - o1.getId();
            }
        });
        return messageList;
    }
}
