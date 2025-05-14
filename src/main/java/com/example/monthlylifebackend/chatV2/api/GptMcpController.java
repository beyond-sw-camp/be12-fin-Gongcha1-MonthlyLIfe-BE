package com.example.monthlylifebackend.chatV2.api;

import com.example.monthlylifebackend.chatV2.api.model.req.UserRequest;
import com.example.monthlylifebackend.chatV2.service.GptMcpServer;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mcp2")
@RequiredArgsConstructor
@Slf4j
public class GptMcpController {

    private final GptMcpServer gptMcpServer;

    @PostMapping("/chat")
    public BaseResponse<?>  handle(@RequestBody UserRequest userRequest , @AuthenticationPrincipal User user) {
        long start = System.currentTimeMillis(); // ⏱ 요청 시작 시간


        System.out.println(userRequest.message());


        try {
            // ... 너의 기존 MCP 로직

            Object rs = gptMcpServer.handleMcp(user.getId() , userRequest);
            return BaseResponse.onSuccess(rs);


        } finally {
            long end = System.currentTimeMillis(); // ⏱ 응답 직전 시간
            log.info("요청 처리 시간: {} ms", (end - start));
        }


    }
}