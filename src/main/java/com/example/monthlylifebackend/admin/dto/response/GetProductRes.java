package com.example.monthlylifebackend.admin.dto.response;

import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "상품 재고 요약 응답")
public class GetProductRes {

    @Schema(description = "상품 ID", example = "Product001")
    private String productCode;

    @Schema(description = "상품명", example = "무선 헤드폰")
    private String productName;

    @Schema(description = "상품 이미지 리스트")
    private List<ProductImageRes> productImages;

    @Schema(description = "제조사", example = "삼성")
    private String manufacturer;

    @Schema(description = "전체 재고 수량", example = "100")
    private Long totalStockCount;

    @Schema(description = "가용 재고 수량", example = "50")
    private Long availableStockCount;

    @Schema(description = "최초 등록일", example = "2024-04-10T10:15:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime createdAt;

    /**
     * JPQL 결과 매핑용 생성자
     * @param productCode 상품 코드
     * @param productName 상품명
     * @param manufacturer 제조사
     * @param totalStockCount 총 재고 수량
     * @param availableStockCount 가용 재고 수량
     * @param createdAt 생성일시
     */
    public GetProductRes(
            String productCode,
            String productName,
            String manufacturer,
            Long totalStockCount,
            Long availableStockCount,
            LocalDateTime createdAt
    ) {
        this.productCode        = productCode;
        this.productName        = productName;
        this.manufacturer       = manufacturer;
        this.totalStockCount    = totalStockCount;
        this.availableStockCount= availableStockCount;
        this.createdAt          = createdAt;
        this.productImages      = Collections.emptyList();
    }
}

