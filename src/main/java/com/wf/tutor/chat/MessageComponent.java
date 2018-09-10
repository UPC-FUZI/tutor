package com.wf.tutor.chat;

import com.wf.tutor.dao.MessageMapper;
import com.wf.tutor.domain.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class MessageComponent {
    @Autowired
    private MessageMapper messageMapper;

    public static MessageComponent messageComponent;

    @PostConstruct
    public void init() {
        messageComponent = this;
        messageComponent.messageMapper = this.messageMapper;
    }

    public static int insert(Message record) {
        return messageComponent.messageMapper.insert(record);
    }

    //更新为已读
    public static int updateById(Integer id) {
        return messageComponent.messageMapper.updateById(id);
    }

    public static List<Message> selectByFromId(String fromId, Integer status) {
        return messageComponent.messageMapper.selectByFromId(fromId, status);
    }

    public static List<Message> selectByFromIdAndToId(String fromId, String toId, Integer status) {
        return messageComponent.messageMapper.selectByFromIdAndToId(fromId, toId, status);
    }
}
