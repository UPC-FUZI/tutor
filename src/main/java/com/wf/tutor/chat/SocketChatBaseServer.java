package com.wf.tutor.chat;

import com.wf.tutor.common.LoggerBase;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SocketChatBaseServer extends LoggerBase {
    protected static final int NO_SEND_STATUS = 0;
    /**
     * 全部在线会话  PS: 基于场景考虑 这里使用线程安全的Map存储会话对象。
     * Map<userId,Session>
     */
    protected static Map<String, Session> onlineSessions = new ConcurrentHashMap<>();


}
