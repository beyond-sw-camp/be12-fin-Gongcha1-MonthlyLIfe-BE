package com.example.monthlylifebackend.chatV2.api.model.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "AI채팅 상품 상세 조회 응답 DTO")
public class GetProductDetailAiChatRes {

    @Schema(description = "상품 이름", example = "삼성 공기청정기 AX60")
    private String name;

    @Schema(description = "상품 설명 이미지", example = "https://test.png")
    private String descriptionImageUrl;

    @Schema(description = "상품 코드", example = "AX60001")
    private String code;


}
