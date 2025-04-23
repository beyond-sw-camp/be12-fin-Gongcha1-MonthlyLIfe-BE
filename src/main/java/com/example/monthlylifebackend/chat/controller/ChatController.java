package com.example.monthlylifebackend.chat.controller;

import com.example.monthlylifebackend.chat.service.ChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
@Tag(name = "Chat", description = "채팅 관련 API (사용자/관리자)")
public class ChatController {

    private final ChatService chatService;

    @Operation(summary = "사용자 채팅 메시지 전송", description = "사용자가 채팅 메시지를 전송합니다.")
    @PostMapping("/user/send")
    public void sendUserChatMessage() {
        // 사용자 채팅 메시지 전송 로직 구현
    }

    @Operation(summary = "관리자 채팅 메시지 전송", description = "관리자가 채팅 메시지를 전송합니다.")
    @PostMapping("/admin/send")
    public void sendAdminChatMessage() {
        // 관리자 채팅 메시지 전송 로직 구현
    }

    @Operation(summary = "사용자 단일 채팅방 조회", description = "사용자가 관리자와의 1:1 채팅방 대화를 조회합니다.")
    @GetMapping("/user")
    public void getUserChatRoom() {
        // 사용자 채팅방 조회 로직 구현
    }

    @Operation(summary = "관리자 모든 채팅방 리스트 조회", description = "관리자가 전체 채팅방 목록을 조회합니다.")
    @GetMapping("/admin/list")
    public void getAdminChatRooms() {
        // 관리자 채팅방 목록 조회 로직 구현
    }

    @Operation(summary = "관리자 단일 채팅방 조회", description = "관리자가 특정 채팅방의 대화 내용을 조회합니다.")
    @GetMapping("/admin/{roomId}")
    public void getAdminChatRoom(@PathVariable("roomId") Long roomId) {
        // 관리자 채팅방 상세 조회 로직 구현
    }
}
