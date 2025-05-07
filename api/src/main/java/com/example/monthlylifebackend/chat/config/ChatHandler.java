package com.example.monthlylifebackend.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class ChatHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();
    private final Map<String, WebSocketSession> adminSessions = new ConcurrentHashMap<>();
    private final Set<String> userNicknames = ConcurrentHashMap.newKeySet();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String nickname = getParam(session, "nickname");
        String role = getParam(session, "role");

        if ("admin".equals(role)) {
            adminSessions.put(nickname, session);
            System.out.println("🔑 관리자 접속: " + nickname);
        } else {
            userSessions.put(nickname, session);
            userNicknames.add(nickname);
            System.out.println("👤 유저 접속: " + nickname);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);

        WebSocketSession targetSession = null;

        if ("admin".equals(chatMessage.getFrom())) {
            // 관리자가 특정 유저에게
            targetSession = userSessions.get(chatMessage.getTo());
        } else {
            // 유저가 관리자에게 (단일 관리자만 있다고 가정)
            targetSession = adminSessions.values().stream().findFirst().orElse(null);
        }

        if (targetSession != null && targetSession.isOpen()) {
            targetSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String nickname = getParam(session, "nickname");
        String role = getParam(session, "role");

        if ("admin".equals(role)) {
            adminSessions.remove(nickname);
        } else {
            userSessions.remove(nickname);
            userNicknames.remove(nickname);
        }

        System.out.println("❌ 연결 해제: " + nickname);
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

    public List<String> getActiveUsers() {
        return new ArrayList<>(userNicknames);
    }
}
