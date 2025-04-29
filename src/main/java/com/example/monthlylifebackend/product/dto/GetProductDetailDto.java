package com.example.monthlylifebackend.product.dto;

import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
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
@Schema(description = "상품 상세 조회 응답 DTO")
public class GetProductDetailDto {

    @Schema(description = "상품 이름", example = "오브제 미니 냉장고")
    private String productName;

    @Schema(description = "상품 코드", example = "OBJ-2024-01")
    private String productCode;

    @Schema(description = "상품 설명", example = "소형 오브제 미니 냉장고입니다. 다양한 색상으로 제공됩니다.")
    private String productDescription;

    @Schema(description = "상품 설명 이미지 URL", example = "https://cdn.example.com/images/product-description/obj-mini.jpg")
    private String productDescriptionImageUrl;

    @Schema(description = "제조사", example = "LG전자")
    private String manufacturer;

    @Schema(description = "상품 이미지 목록")
    private List<ProductImageRes> productImages;
}
