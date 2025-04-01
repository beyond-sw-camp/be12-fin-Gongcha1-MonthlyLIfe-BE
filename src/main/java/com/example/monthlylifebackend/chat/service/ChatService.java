package com.example.monthlylifebackend.chat.service;


import com.example.monthlylifebackend.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class ChatService {



    private final ChatRepository chatRepository;
}
