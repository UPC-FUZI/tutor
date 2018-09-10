package com.wf.tutor.chat;

import com.wf.tutor.common.LoggerBase;
import com.wf.tutor.domain.Message;
import com.wf.tutor.util.JsonUtil;
import org.springframework.stereotype.Service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket 聊天服务端
 *
 * @see ServerEndpoint WebSocket服务端 需指定端点的访问路径
 * @see Session   WebSocket会话对象 通过它给客户端发送消息
 */

@Service
@ServerEndpoint("/chat/{fromId}/{toId}")
public class SocketChatSendServer extends SocketChatBaseServer {

    /**
     * 当客户端打开连接：1.添加会话对象
     * 2.发送历史消息
     */
    @OnOpen
    public void onOpen(@PathParam("fromId") String fromId, @PathParam("toId") String toId, Session session) {
        onlineSessions.put(fromId, session);
        sendOldMessageToMe(fromId, toId);
        getLogger().info("打开会话[fromId:{}][toId:{}][BasicRemote:{}]", fromId, toId, session.getBasicRemote());
    }

    /**
     * 当客户端发送消息：获得发送人ID、接收人ID、消息
     * 接收人ID为null时，广播发送
     */
    @OnMessage
    public void onMessage(String jsonStr) {
        Message message = JsonUtil.jsonToObject(jsonStr, Message.class);
        if (message.getToId() == null) {
            sendMessageToAll(JsonUtil.objectToJson(message));
        } else {
            sendMessageToOne(message.getToId(), message);
        }
    }

    /**
     * 当关闭连接：移除会话对象
     */
    @OnClose
    public void onClose(Session session) {
        onlineSessions.forEach((fromId, se) -> {
            if (se.equals(session)) {
                onlineSessions.remove(fromId);
                getLogger().info("移除会话[fromId:{}][BasicRemote:{}]", fromId, session.getBasicRemote());
            }
        });
    }

    /**
     * 公共方法：发送信息给所有人
     */
    private void sendMessageToAll(String msg) {
        onlineSessions.forEach((id, session) -> {
            try {
                session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                getLogger().error("发送信息给所有人:{}", e);
            }
        });
    }

    /**
     * 公共方法：发送实时信息给某一个人
     */
    private void sendMessageToOne(String toId, Message msg) {
        onlineSessions.forEach((userId, session) -> {
            if (toId.equals(userId)) {
                try {
                    session.getBasicRemote().sendText(JsonUtil.objectToJson(msg));
                    msg.setStatus(1);
                    MessageComponent.insert(msg);
                } catch (IOException e) {
                    msg.setStatus(0);
                    MessageComponent.insert(msg);
                    getLogger().error("发送实时信息给某一个人:[{}]{}", msg, e);
                    return;
                }
            } else {
                msg.setStatus(0);
                MessageComponent.insert(msg);
            }
        });
    }

    /**
     * 公共方法：发送历史信息给某一个人
     */
    private void sendOldMessageToMe(String fromId, String toId) {
        List<Message> messageList = MessageComponent.selectByFromIdAndToId(toId, fromId, NO_SEND_STATUS);
        if (messageList == null || messageList.isEmpty()) {
            return;
        }
        onlineSessions.forEach((userId, session) -> {
            if (fromId.equals(userId)) {
                try {
                    for (Message msg : messageList) {
                        session.getBasicRemote().sendText(JsonUtil.objectToJson(msg));
                        msg.setStatus(1);
                        MessageComponent.updateById(msg.getId());
                    }
                } catch (IOException e) {
                    getLogger().error("发送历史信息给某一个人:{}", e);
                }
            }
        });
    }

}
