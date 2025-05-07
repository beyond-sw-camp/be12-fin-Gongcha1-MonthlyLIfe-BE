package com.example.monthlylifebackend.chat.service;


//import com.example.monthlylifebackend.chat.repository.ChatRepository;

import com.example.monthlylifebackend.chat.config.ChatHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class ChatService {

    private final ChatHandler chatHandler;

    public List<String> getActiveUsers() {
        return chatHandler.getActiveUsers();
    }

}
