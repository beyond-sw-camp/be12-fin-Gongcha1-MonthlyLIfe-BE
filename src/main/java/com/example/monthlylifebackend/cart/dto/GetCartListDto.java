package com.example.monthlylifebackend.cart.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCartListDto {
    @Schema(description = "구독 상세 Idx", example = "101")
    private Long saleidx;

    @Schema(description = "상품 이름", example = "혼라이프 패키지")
    private String salename;

    @Schema(description = "구독 기간 (개월)", example = "3")
    private int period;

    @Schema(description = "상품 가격", example = "29900")
    private int price;

    private String productCode;
}
