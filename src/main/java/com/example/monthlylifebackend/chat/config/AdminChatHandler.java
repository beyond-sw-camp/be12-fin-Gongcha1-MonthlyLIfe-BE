package com.example.monthlylifebackend.chat.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.net.URI;
import java.util.Map;

@Component
public class AdminChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String nickname = getParam(session, "nickname");

        if (nickname == null || nickname.isBlank()) {
            System.out.println("⚠️ 관리자 닉네임이 비어있어 연결 종료됨");
            session.close(CloseStatus.BAD_DATA);
            return;
        }

        System.out.println("🔑 관리자 접속: " + nickname);

        // ✅ 여기서 사용되고 있음
        UserChatHandler.setAdminSession(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (!session.isOpen()) return;

        ChatMessage chatMessage = objectMapper.readValue(message.getPayload(), ChatMessage.class);
        String target = chatMessage.getTo();

        Map<String, WebSocketSession> userSessions = UserChatHandler.getUserSessionMap();

        WebSocketSession userSession = userSessions.get(target);
        if (userSession != null && userSession.isOpen()) {
            userSession.sendMessage(new TextMessage(objectMapper.writeValueAsString(chatMessage)));
        } else {
            System.out.println("⚠️ 유저 [" + target + "] 세션이 없거나 닫혀 있음");
            ChatMessage errorMsg = new ChatMessage();
            errorMsg.setFrom("system");
            errorMsg.setTo(chatMessage.getFrom());
            errorMsg.setText("⚠️ 현재 유저새끼 연결이 되어있지 않습니다. 잠시 후 다시 시도해주세요.");
            session.sendMessage(new TextMessage(objectMapper.writeValueAsString(errorMsg)));

        }
    }


    private String getParam(WebSocketSession session, String key) {
        URI uri = session.getUri();
        if (uri == null) return "";
        return java.util.Arrays.stream(uri.getQuery().split("&"))
                .filter(p -> p.startsWith(key + "="))
                .map(p -> p.split("=")[1])
                .findFirst()
                .orElse("");
    }
}
