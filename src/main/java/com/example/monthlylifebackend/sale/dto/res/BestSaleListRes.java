package com.example.monthlylifebackend.sale.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Best 판매상품 응답 DTO")
public class BestSaleListRes {

    @Schema(description = "판매 상품 고유 ID", example = "101")
    private Long saleIdx;

    @Schema(description = "판매 상품 이름", example = "삼성 공기청정기")
    private String name;

    @Schema(description = "판매 상품 설명", example = "미세먼지를 효과적으로 제거하는 고성능 제품")
    private String description;

    @Schema(description = "카테고리 ID", example = "3")
    private Long categoryIdx;

    @Schema(description = "판매 상품에 포함된 상품 리스트")
    private List<SaleProductInfo> productList;

    @Schema(description = "판매 상품 가격 리스트")
    private List<SalePriceInfo> priceList;

    @Schema(description = "해당 상품의 구독 수", example = "25")
    private Long subscribeCount;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "판매 상품 내 포함된 개별 상품 정보")
    public static class SaleProductInfo {

        @Schema(description = "상품 코드", example = "TV001")
        private String productCode;

        @Schema(description = "상품 상태(Condition) ID", example = "2")
        private Long conditionIdx;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "판매 상품 기간별 가격 정보")
    public static class SalePriceInfo {

        @Schema(description = "렌탈 기간 (개월 단위)", example = "12")
        private int period;

        @Schema(description = "해당 기간의 가격", example = "15000")
        private int price;
    }
}
