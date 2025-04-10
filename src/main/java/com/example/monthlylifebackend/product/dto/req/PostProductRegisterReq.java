package com.example.monthlylifebackend.product.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class PostProductRegisterReq {

    @Schema(description = "상품 이름 (한글, 영문자, 숫자 2 ~ 30자)", example = "삼성TV123")
    @NotNull(message = "상품 이름은 null일 수 없습니다.")
    @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,30}$", message = "상품 이름은 한글, 영문, 숫자 포함 2~30자여야 합니다.")
    private String name;

    @Schema(description = "상품 코드 (고유 식별자)", example = "PRD001")
    @NotNull(message = "상품 코드는 null일 수 없습니다.")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}$", message = "상품 코드는 영문자와 숫자로 6~20자여야 합니다.")
    private String code;

    @Schema(description = "상품 설명", example = "이 상품은 최신 기능을 갖춘 제품입니다.")
    private String description;

    @Schema(description = "제조사", example = "삼성전자")
    private String manufacturer;

    @Schema(description = "상품 이미지 리스트")
    private List<PostProductImageReq> productImages;
}
