package com.example.monthlylifebackend.chat.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer {
    private final UserChatHandler userChatHandler;
    private final AdminChatHandler adminChatHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(userChatHandler, "/ws/chat")
                .setAllowedOrigins("*");

        registry.addHandler(adminChatHandler, "/ws/adminchat")
                .setAllowedOrigins("*");
    }



}


