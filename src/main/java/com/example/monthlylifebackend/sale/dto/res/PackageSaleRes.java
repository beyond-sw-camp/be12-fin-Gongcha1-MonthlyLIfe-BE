package com.example.monthlylifebackend.sale.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "패키지 판매상품 응답 DTO")
public class PackageSaleRes {

    @Schema(description = "판매 상품 ID", example = "101")
    private Long saleIdx;

    @Schema(description = "판매 상품 이름", example = "홈케어 패키지")
    private String name;

    @Schema(description = "판매 상품 설명", example = "공기청정기와 로봇청소기로 구성된 홈케어 패키지")
    private String description;

    @Schema(description = "카테고리 ID", example = "4")
    private Long categoryIdx;

    @Schema(description = "포함된 개별 상품 리스트")
    private List<ProductInfo> productList;

    @Schema(description = "기간별 가격 리스트")
    private List<PriceInfo> priceList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "패키지 구성 개별 상품 정보")
    public static class ProductInfo {

        @Schema(description = "상품 코드", example = "AIR1234")
        private String productCode;

        @Schema(description = "상품 이미지 URL 리스트", example = "[\"https://example.com/img1.jpg\", \"https://example.com/img2.jpg\"]")
        private List<String> imageUrls;

        @Schema(description = "상품 상태(등급) 이름", example = "S급")
        private String conditionName;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Schema(description = "패키지 가격 정보")
    public static class PriceInfo {

        @Schema(description = "렌탈 기간 (개월)", example = "12")
        private int period;

        @Schema(description = "가격", example = "18000")
        private int price;
    }
}
