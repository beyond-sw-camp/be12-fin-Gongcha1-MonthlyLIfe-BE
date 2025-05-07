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
public class NewSaleListRes {
    @Schema(description = "판매 ID")
    private Long idx;
    @Schema(description = "상품 이름")
    private String name;
    @Schema(description = "카테고리 ID")
    private Long categoryIdx;
    @Schema(description = "썸네일 이미지 URL")
    private String imageUrl;
    @Schema(description = "등급 이름")
    private String conditionName;
    @Schema(description = "최저가")
    private int price;
    @Schema(description = "해당 최저가 기간(개월)")
    private int period;
}
