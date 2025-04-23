package com.example.monthlylifebackend.admin.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

// ✅ 현재 클래스명 잘못됨. GetItemRes → GetProductStockSummaryRes 로 수정

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "상품 재고 요약 응답")
public class GetProductRes {

    @Schema(description = "상품 ID", example = "Product001")
    private String productCode;

    @Schema(description = "상품명", example = "무선 헤드폰")
    private String productName;

    @Schema(description = "제조사", example = "삼성")
    private String manufacturer;

    @Schema(description = "전체 재고 수량", example = "100")
    private Long totalStockCount;

    @Schema(description = "가용 재고 수량", example = "50")
    private Long availableStockCount;

    @Schema(description = "최초 등록일", example = "2024-04-10T10:15:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;
}

