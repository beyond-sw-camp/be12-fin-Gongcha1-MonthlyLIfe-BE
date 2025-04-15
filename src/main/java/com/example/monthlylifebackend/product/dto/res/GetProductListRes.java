package com.example.monthlylifebackend.product.dto.res;

import com.example.monthlylifebackend.product.dto.req.PostProductImageReq;
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
@Schema(description = "상품 목록 조회 응답 DTO")
public class GetProductListRes {

    @Schema(description = "상품 이름", example = "삼성 공기청정기 AX60")
    private String name;

    @Schema(description = "상품 코드", example = "AIR001")
    private String code;

    @Schema(description = "제조사", example = "삼성전자")
    private String manufacturer;

    @Schema(description = "상품 설명", example = "초미세먼지 집진 필터로 강력한 공기청정 기능을 제공")
    private String description;

    @Schema(description = "상품 위치 상태 (창고, 대여중, 수리중 등)", example = "창고")
    private String location;

    @Schema(description = "상품 상태 등급 (S, A, B, C 중 하나)", example = "A")
    private String condition;
    
    @Schema(description = "상품 개수")
    private int count;

    @Schema(description = "상품 이미지 리스트")
    private List<PostProductImageReq> productImages;
}
