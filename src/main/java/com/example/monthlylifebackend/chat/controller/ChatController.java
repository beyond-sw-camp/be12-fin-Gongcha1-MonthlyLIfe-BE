package com.example.monthlylifebackend.chat.controller;

import com.example.monthlylifebackend.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
@Tag(name = "Chat", description = "채팅 관련 API (사용자/관리자)")
public class ChatController {

        private final ChatService chatService;

        @GetMapping("/active-users")
        public ResponseEntity<List<String>> getActiveUsers() {
            return ResponseEntity.ok(chatService.getActiveUsers());
        }

}
