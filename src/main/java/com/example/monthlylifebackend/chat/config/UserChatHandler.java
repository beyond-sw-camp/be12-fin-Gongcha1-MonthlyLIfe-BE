package com.example.monthlylifebackend.chat.config;


import com.example.monthlylifebackend.chat.config.ChatMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserChatHandler extends TextWebSocketHandler {
    private static final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static WebSocketSession adminSession = null;
    public static Map<String, WebSocketSession> getUserSessionMap() {
        return userSessions;
    }
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String nickname = getParam(session, "nickname");

        if (nickname == null || nickname.isBlank()) {
            System.out.println("⚠️ 유저 닉네임이 비어있어 연결 종료됨");
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        userSessions.put(nickname, session);
        System.out.println("👤 유저 접속: " + nickname);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        if (adminSession != null && adminSession.isOpen()) {
            adminSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else {
            // 관리자 연결 안 되어있으면 유저에게 에러 메시지 전송
            ChatMessage errorMsg = new ChatMessage();
            errorMsg.setFrom("system");
            errorMsg.setTo(chatMessage.getFrom());  // 유저 본인에게
            errorMsg.setText("⚠️ 현재 관리자 연결이 되어있지 않습니다. 잠시 후 다시 시도해주세요.");

            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(errorMsg)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String nickname = getParam(session, "nickname");
        userSessions.remove(nickname);
        System.out.println("❌ 유저 연결 해제: " + nickname);
    }

    public static void setAdminSession(WebSocketSession session) {
        adminSession = session;
        System.out.println("✅ 관리자 세션 설정됨");
    }


    private String getParam(WebSocketSession session, String key) {
        URI uri = session.getUri();
        if (uri == null) return "";
        return Arrays.stream(uri.getQuery().split("&"))
                .filter(p -> p.startsWith(key + "="))
                .map(p -> p.split("=")[1])
                .findFirst()
                .orElse("");
    }

    public static Set<String> getConnectedUsers() {
        return userSessions.keySet();
    }

}