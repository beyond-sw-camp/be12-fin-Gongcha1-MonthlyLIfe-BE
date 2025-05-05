package com.example.monthlylifebackend.chatV2.protocol;

import java.util.List;
import java.util.Map;

public class ProtocolBuilder {

    public static Map<String, Object> buildRequest(String userMessage ,List<String> conversation) {
        return Map.of(
                "model", "gpt-4-1106-preview",
                "messages", List.of(
                        Map.of("role", "system", "content", """
                                너는 렌탈 구독 도우미야.
                                사용자 질문을 분석해서 item(상품명)과 duration(개월 수)을 추출해줘.
                                요청에 맞는 함수 호출을 해야해. 예: 구독, 취소, 조회 등.
                                반드시 "item" 필드는 비어 있지 않은 문자열이어야 한다.
                                          
                                빈 문자열("\\n"만 있는 경우 포함)을 arguments로 넘기지 말 것.
                                무조건 하나의 tools는 골라야함
                                
                                
                                아래는 사용자와의 전 대화 내용이다:
                                """+conversation),
                        Map.of("role", "user", "content", userMessage)
                ),
                "tools", List.of(
                        Map.of(
                                "type", "function",
                                "function", Map.of(
                                        "name", "search_item",
                                        "description", "상품 검색 요청",
                                        "parameters", buildParameters()
                                )
                        ),
                        Map.of(
                                "type", "function",
                                "function", Map.of(
                                        "name", "subscribe_item",
                                        "description", "상품 구독 요청",
                                        "parameters", buildParameters()
                                )
                        ),
                        Map.of(
                                "type", "function",
                                "function", Map.of(
                                        "name", "cancel_item",
                                        "description", "상품 구독 취소 요청",
                                        "parameters", buildParameters()
                                )
                        ),
                        Map.of(
                                "type", "function",
                                "function", Map.of(
                                        "name", "extend_item",
                                        "description", "구독 연장 요청",
                                        "parameters", buildParameters()
                                )
                        )
                ),
                "tool_choice", "auto"
        );
    }

    private static Map<String, Object> buildParameters() {
        return Map.of(
                "type", "object",
                "properties", Map.of(
                        "item", Map.of(
                                "type", "string",
                                "description", "상품 이름"
                        ),
                        "duration", Map.of(
                                "type", "integer",
                                "description", "기간(개월 수)"
                        )
                ),
                "required", List.of("item")
        );
    }
}
