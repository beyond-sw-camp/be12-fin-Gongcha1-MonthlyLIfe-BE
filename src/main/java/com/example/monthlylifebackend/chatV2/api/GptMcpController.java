package com.example.monthlylifebackend.chatV2.api;

import com.example.monthlylifebackend.chatV2.api.model.req.UserRequest;
import com.example.monthlylifebackend.chatV2.service.GptMcpServer;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mcp2")
@RequiredArgsConstructor
public class GptMcpController {

    private final GptMcpServer gptMcpServer;

    @PostMapping("/chat")
    public Object  handle(@RequestBody UserRequest userRequest , @AuthenticationPrincipal User user) {


        System.out.println(userRequest.message());

        return gptMcpServer.handleMcp(user.getId() , userRequest);
    }
}