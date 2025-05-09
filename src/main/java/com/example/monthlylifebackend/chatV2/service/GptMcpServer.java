package com.example.monthlylifebackend.chatV2.service;

import com.example.monthlylifebackend.chatV2.api.model.req.UserRequest;
import com.example.monthlylifebackend.chatV2.api.model.res.GptParsedResult;
import com.example.monthlylifebackend.chatV2.core.CommandDispatcher;
import com.example.monthlylifebackend.chatV2.core.InternalCommand;
import com.example.monthlylifebackend.chatV2.core.UserContext;
import com.example.monthlylifebackend.chatV2.core.UserContextManager;
import com.example.monthlylifebackend.chatV2.executor.InternalCommandExecutor;
import com.example.monthlylifebackend.chatV2.protocol.ProtocolBuilder;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GptMcpServer {

    @Value("${openai.api-key}")
    private String openAiApiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final ProductRepository productRepository;
    private final UserContextManager userContextManager;
    private final CommandDispatcher commandDispatcher;
    private final InternalCommandExecutor internalCommandExecutor;

    /**
     * 자연어를 받아서 InternalCommand로 변환하는 메인 메서드
     */
    @Transactional
    public Object handleMcp(String userId, UserRequest userRequest) {
        try {
//            System.out.println("@@@@@@@@@@@@@@@@@@ : " + userId);
            userContextManager.addMessageToConversationLog(userId, "사용자: " + userRequest.message());
            List<String> conversation = userContextManager.getConversationLog(userId);


            GptParsedResult parsed = validateParsedResult(callGptAndParse(userRequest.message(), conversation));

            UserContext context = userContextManager.getContext(userId);
            InternalCommand command = commandDispatcher.dispatch(context, parsed);

            if (command.getService() == "search") {

                return internalCommandExecutor.execute(command);
            } else if (command.getService() == "rental") {

//
//                String confirmationMessage = "이 제품으로 구독하시겠습니까? (예/아니요)";
//                userContextManager.addMessageToConversationLog(userId, "AI: " + confirmationMessage);


                return internalCommandExecutor.execute(command);


            } else {
                return null;
            }


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("MCP 서버 오류", e);
        }
    }


    private GptParsedResult validateParsedResult(GptParsedResult parsed) {
        if (parsed.item() == null || parsed.item().trim().isEmpty()) {
            throw new IllegalArgumentException("GPT가 이상한 item 반환함: " + parsed.item());
        }
        return parsed;
    }


    private GptParsedResult callGptAndParse(String message, List<String> conversation) throws Exception {

//        System.out.println("@@@@@@@@@@@@@@@@@@@@@" + conversation);
        Map<String, Object> requestBody = ProtocolBuilder.buildRequest(message, conversation);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiApiKey);

        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                entity,
                Map.class
        );

        Map messageMap = (Map) ((Map) ((List<?>) response.getBody().get("choices")).get(0)).get("message");

        List<Map<String, Object>> toolCalls = (List<Map<String, Object>>) messageMap.get("tool_calls");
        if (toolCalls == null || toolCalls.isEmpty()) {
            System.err.println("Error: No tool_calls returned from GPT API.");
            throw new IllegalStateException("GPT tool_calls 없음");
        }

        Map<String, Object> firstToolCall = toolCalls.get(0);
        Map<String, Object> function = (Map<String, Object>) firstToolCall.get("function");

        System.out.println(messageMap);


        String functionName = (String) function.get("name");
        String argumentsJson = (String) function.get("arguments");

        JsonNode args = objectMapper.readTree(argumentsJson);

        String item = args.has("item") ? args.get("item").asText() : "";
        item = item.replaceAll("\\s+", " ").trim();
        Integer period = (args.has("period") && !args.get("period").isNull())
                ? args.get("period").asInt() : null;

        String condition = null;
        if ("subscribe_item".equals(functionName) && args.has("condition") && !args.get("condition").isNull()) {
            condition = args.get("condition").asText();
        }

        return new GptParsedResult(functionName, item, period, condition);
    }
}
