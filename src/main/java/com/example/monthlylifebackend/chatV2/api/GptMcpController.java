package com.example.monthlylifebackend.chatV2.api;

import com.example.monthlylifebackend.chatV2.api.model.req.UserRequest;
import com.example.monthlylifebackend.chatV2.service.GptMcpServer;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Chat", description = "GPT 기반 MCP 챗봇 API")
public class GptMcpController {

    private final GptMcpServer gptMcpServer;

    @Operation(
            summary = "GPT MCP 채팅 요청 처리",
            description = "사용자의 메시지를 받아 MCP 서버를 통해 GPT 응답을 처리하고 결과를 반환합니다."
    )
    @PostMapping("/chat")
    public BaseResponse<?> handle(@RequestBody UserRequest userRequest,
                                  @AuthenticationPrincipal User user) {
        System.out.println(userRequest.message());

        // GPT MCP 처리 로직
        Object rs = gptMcpServer.handleMcp(user.getId(), userRequest);
        return BaseResponse.onSuccess(rs);

    }

}
