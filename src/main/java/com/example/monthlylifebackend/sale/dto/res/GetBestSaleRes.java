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
@Schema(description = "카테고리별 또는 전체 Best 판매상품 응답 DTO")
public class GetBestSaleRes {

    @Schema(description = "판매 상품 고유 ID", example = "101")
    private Long saleIdx;

    @Schema(description = "판매 상품 이름", example = "삼성 청소기 패키지")
    private String name;

    @Schema(description = "카테고리 ID", example = "4")
    private Long categoryIdx;

    @Schema(description = "상품 대표 이미지 URL", example = "https://example.com/images/samsung_vacuum.jpg")
    private String ImageUrl;

    @Schema(description = "제조사", example = "삼성전자")
    private String manufacturer;

    @Schema(description = "상품 상태(등급) 이름", example = "S급")
    private String conditionName;

    @Schema(description = "최저 가격", example = "12000")
    private int price;

    @Schema(description = "최저 가격이 적용되는 기간 (개월)", example = "6")
    private int period;

    @Schema(description = "해당 상품의 구독 수", example = "35")
    private Long subscribeCount;
}
