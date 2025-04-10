package com.example.monthlylifebackend.product.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 목록 조회 응답 DTO")
public class GetProductListRes {

    @Schema(description = "상품 이름", example = "삼성 공기청정기 AX60")
    private String name;

    @Schema(description = "제조사", example = "삼성전자")
    private String manufacturer;

    @Schema(description = "상품 설명", example = "초미세먼지 집진 필터로 강력한 공기청정 기능을 제공")
    private String description;
}
