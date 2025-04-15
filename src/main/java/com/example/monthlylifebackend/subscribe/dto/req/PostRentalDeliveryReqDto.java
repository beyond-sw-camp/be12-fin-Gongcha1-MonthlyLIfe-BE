package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "구독 + 렌탈 배송 요청 DTO")
public class PostRentalDeliveryReqDto {

    @Schema(description = "상품 정보 리스트", required = true)
    private List<ProductRequestDto> products;

    @Schema(description = "수령인 이름", example = "홍길동")
    private String recipientName;

    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhone;

    @Schema(description = "우편번호", example = "12345")
    private String postalCode;

    @Schema(description = "주소 1", example = "서울특별시 강남구 테헤란로 123")
    private String address1;

    @Schema(description = "주소 2", example = "101동 202호")
    private String address2;

    @Schema(description = "배송 상태", example = "Shipped")
    private String status;

    @Schema(description = "택배사", example = "CJ대한통운")
    private String courierCompany;

    @Schema(description = "운송장 번호", example = "ABC123456789")
    private String trackingNumber;

    @Schema(description = "배송 메모", example = "문 앞에 놔주세요")
    private String deliveryMemo;

    @Schema(description = "출고 시간", example = "2025-04-14T15:00:00")
    private String shippedAt;

    @Schema(description = "배송 완료 시간", example = "2025-04-15T12:00:00")
    private String deliveredAt;
}
