package com.example.monthlylifebackend.sale.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Schema(description = "판매 상품 등록 요청 DTO")
public class PostSaleRegisterReq {
    @NotBlank(message = "판매 상품명은 필수입니다.")
    @Schema(description = "판매 상품명", example = "프리미엄 청소기 세트")
    private String name;

    @Schema(description = "판매 설명", example = "고성능 무선 청소기")
    private String description;

    @NotNull(message = "카테고리 IDX는 필수입니다.")
    @Schema(description = "카테고리 IDX", example = "25")
    private Long categoryIdx;

    @Schema(description = "판매 상품 구성 목록")
    private List<SaleProductInfo> saleProducts;

    @Schema(description = "기간별 가격 정보 목록")
    private List<SalePriceReq> salePrices;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "판매 상품 구성 정보")
    public static class SaleProductInfo {

        @NotBlank(message = "상품 코드는 필수입니다.")
        @Schema(description = "상품 코드", example = "VACUUM001")
        private String productCode;

        @NotNull(message = "상품 상태(Condition) IDX는 필수입니다.")
        @Schema(description = "상품 상태(Condition) IDX", example = "3")
        private Long conditionIdx;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "판매 가격 정보")
    public static class SalePriceReq {

        @Schema(description = "기간(개월)", example = "6")
        private int period;

        @Min(value = 0, message = "가격은 0원 이상이어야 합니다.")
        @Schema(description = "가격", example = "12900")
        private int price;
    }
}
