package com.example.monthlylifebackend.chat.config;

import lombok.Data;

@Data
public class ChatMessage {
    private String from;
    private String to;
    private String text;
}
