package com.example.monthlylifebackend.sale.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "신규 등록 판매상품 응답 DTO")
public class NewSaleListRes {

    @Schema(description = "판매 ID", example = "101")
    private Long idx;

    @Schema(description = "상품 이름", example = "LG 미니 냉장고")
    private String name;

    @Schema(description = "카테고리 ID", example = "2")
    private Long categoryIdx;

    @Schema(description = "썸네일 이미지 URL", example = "https://example.com/images/fridge_thumb.jpg")
    private String imageUrl;

    @Schema(description = "등급 이름", example = "A급")
    private String conditionName;

    @Schema(description = "최저가", example = "9900")
    private int price;

    @Schema(description = "해당 최저가 기간(개월)", example = "12")
    private int period;
}
