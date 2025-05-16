package com.example.monthlylifebackend.chatV2.core;

import com.example.monthlylifebackend.product.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserContextManager {

    private final Map<String, UserContext> contextStore = new ConcurrentHashMap<>();


    public List<String> getConversationLog(String userId) {
        UserContext context = getContext(userId);
        return context.getConversationLog();
    }

    // 유저의 대화 로그에 새로운 메시지 추가
    public void addMessageToConversationLog(String userId, String message) {
        UserContext context = getContext(userId);
        List<String> conversationLog = context.getConversationLog();
        conversationLog.add(message);
        updateContext(userId, context);  // 업데이트된 대화 로그 저장
    }

    public UserContext getContext(String userId) {
        return contextStore.computeIfAbsent(userId, id -> {
            UserContext ctx = new UserContext();
            ctx.setUserId(id);
            return ctx;
        });
    }

    public void updateContext(String userId, UserContext context) {
        contextStore.put(userId, context);
    }


}