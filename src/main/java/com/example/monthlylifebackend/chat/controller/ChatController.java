package com.example.monthlylifebackend.chat.controller;


import com.example.monthlylifebackend.chat.service.ChatService;
import com.example.monthlylifebackend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class ChatController {




    private final ChatService chatService ;


    //todo 사용자용 채팅 메시지 전송 (우선순위 5)
    // POST /chat/user/send
    @PostMapping("/user/send")
    public void sendUserChatMessage() {
        // 사용자 채팅 메시지 전송 로직 구현
    }

    //todo 관리자용 채팅 메시지 전송 (우선순위 5)
    // POST /chat/admin/send
    @PostMapping("/admin/send")
    public void sendAdminChatMessage() {
        // 관리자 채팅 메시지 전송 로직 구현
    }

    //todo 사용자용 단일 채팅방 조회 (우선순위 5)
    // GET /chat/user
    @GetMapping("/user")
    public void getUserChatRoom() {
        // 사용자가 관리자와의 단일 채팅방 대화 조회 로직 구현
    }

    //todo 관리자용 모든 채팅방 리스트 조회 (우선순위 5)
    // GET /chat/admin/list
    @GetMapping("/admin/list")
    public void getAdminChatRooms() {
        // 관리자가 모든 채팅방 리스트를 조회하는 로직 구현
    }

    //todo 관리자용 단일 채팅방 상세 조회 (우선순위 5)
    // GET /chat/admin/{roomId}
    @GetMapping("/admin/{roomId}")
    public void getAdminChatRoom(@PathVariable("roomId") Long roomId) {
        // 관리자가 특정 채팅방의 상세 대화 내용을 조회하는 로직 구현
    }


}
