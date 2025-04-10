package com.example.monthlylifebackend.product.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostProductImageReq {

    @Schema(description = "상품 이미지 URL", example = "https://example.com/image1.jpg")
    private String productImgUrl;
}
