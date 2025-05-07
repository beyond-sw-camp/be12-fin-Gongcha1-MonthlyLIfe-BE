package com.example.monthlylifebackend.product.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 이미지 응답 DTO")
public class ProductImageRes {

    @Schema(description = "이미지 URL", example = "/uploads/uuid_example.jpg")
    private String productImgUrl;
}
