package com.example.monthlylifebackend.product.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostProductRegisterReq {

    @Schema(description = "상품 이름 (한글, 영문자, 숫자 2 ~ 30자)", example = "삼성TV123")
    private String name;

    @Schema(description = "상품 코드 (고유 식별자)", example = "PRD001")
    private String code;

    @Schema(description = "상품 설명", example = "이 상품은 최신 기능을 갖춘 제품입니다.")
    private String description;

    @Schema(description = "상품 설명 이미지", example = "https://test.png")
    private String descriptionImageUrl;

    @Schema(description = "제조사", example = "삼성전자")
    private String manufacturer;

    @Schema(description = "상품 위치 상태 (창고, 대여중, 수리중 등)", example = "창고")
    private String location;

    @Schema(description = "상품 상태 등급 (S, A, B, C 중 하나)", example = "A")
    private String condition;

    @Schema(description = "상품 수량", example = "10")
    private int count;

}
