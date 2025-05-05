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
@Schema(description = "판매 상품 목록 조회 응답 DTO")
public class GetSaleListSliceRes {
    @Schema(description = "판매 ID", example = "10")
    private Long idx;

    @Schema(description = "판매 상품 이름", example = "[특가] 울트라 HD 스마트 TV 75인치 (사은품증정)")
    private String name;

    @Schema(description = "상품 설명", example = "리얼 4K, 6세대 인공지능 알파5, 새로워진 스마트 기능")
    private String description;

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryIdx;

    @Schema(description = "이미지", example = "image.jpg")
    private String imageUrl;

    @Schema(description = "상태", example = "S급")
    private String conditionName;

    @Schema(description = "가격", example = "19900")
    private int price;


}
