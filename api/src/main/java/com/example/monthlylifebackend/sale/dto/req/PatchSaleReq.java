package com.example.monthlylifebackend.sale.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "판매 상품 수정 요청 DTO")
public class PatchSaleReq {

    @Schema(description = "카테고리 IDX", example = "14")
    private Long categoryIdx;

    @Schema(description = "판매 상품명", example = "전자레인지")
    private String name;

    @Schema(description = "판매 설명", example = "최신 전자레인지 상품입니다.")
    private String description;

    @Schema(description = "기간별 가격 리스트")
    private List<SalePriceReq> salePrices = new ArrayList<>();

    @Getter
    @Setter
    @NoArgsConstructor
    @Schema(description = "판매 기간 및 가격 정보")
    public static class SalePriceReq {

        @Schema(description = "기간(개월 수)", example = "6")
        private Integer period;

        @Schema(description = "가격", example = "5900")
        private Long price;
    }
}
