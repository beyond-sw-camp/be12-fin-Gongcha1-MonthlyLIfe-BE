package com.example.monthlylifebackend.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "상품 재고 응답")
public class GetItemListRes {


    @Schema(description = "재고 수량", example = "50")
    private int count;

    @Schema(description = "상품명", example = "무선 헤드폰")
    private String productName;

    @Schema(description = "상품 상태명", example = "A")
    private String conditionName;

    @Schema(description = "상품 위치명", example = "창고 A")
    private String locationName;

    @Schema(description = "카테고리명", example = "전자제품")
    private String categoryName;


    @Schema(description = "재고 등록일", example = "2024-04-10T10:15:30")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
}
