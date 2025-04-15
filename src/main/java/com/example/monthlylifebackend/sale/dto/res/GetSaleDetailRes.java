package com.example.monthlylifebackend.sale.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "판매상품 상세 조회 응답 DTO")
public class GetSaleDetailRes {

    @Schema(description = "판매 상품 이름", example = "[특가] 울트라 HD 스마트 TV 75인치")
    private String name;

    @Schema(description = "판매 상품 설명", example = "리얼 4K, 알파5 AI 탑재, 베젤리스 디자인")
    private String description;

    @Schema(description = "카테고리 ID", example = "1")
    private Long categoryIdx;

    @Schema(description = "포함된 상품 목록")
    private List<ProductInfo> productList;

    @Schema(description = "기간별 가격 목록")
    private List<PriceInfo> priceList;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "포함 상품 정보")
    public static class ProductInfo {
        @Schema(description = "상품 코드", example = "TV001")
        private String productCode;

        @Schema(description = "상품 상태", example = "S급")
        private String conditionName;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "가격 정보")
    public static class PriceInfo {
        @Schema(description = "기간 (개월)", example = "12")
        private int period;

        @Schema(description = "가격", example = "19900")
        private int price;
    }
}
