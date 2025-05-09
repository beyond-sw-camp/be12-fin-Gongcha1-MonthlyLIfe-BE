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
@Schema(description = "AI 추천 구독 상품 상세 응답")
public class GetAiSubscribeDetailRes {

    @Schema(description = "판매 ID", example = "5")
    private Long saleIdx;

    @Schema(description = "판매 가격 ID", example = "12")
    private Long salePriceIdx;



    @Schema(description = "구독 기간 (개월)", example = "6")
    private Integer period;

    @Schema(description = "월 구독 가격", example = "19900")
    private Integer price;


    private String name;
}

