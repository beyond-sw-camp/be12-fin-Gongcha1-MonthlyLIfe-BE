package com.example.monthlylifebackend.subscribe.dto.res;

import com.example.monthlylifebackend.subscribe.dto.SubscribeDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "렌탈 배송 정보 응답 DTO")
public class GetRentalDeliveryInfoRes {

    @Schema(description = "수령인 이름", example = "홍길동")
    private String recipientName;

    @Schema(description = "수령인 전화번호", example = "010-1234-5678")
    private String recipientPhone;

    @Schema(description = "우편번호", example = "04524")
    private String postalCode;

    @Schema(description = "주소1", example = "서울특별시 중구 세종대로 110")
    private String address1;

    @Schema(description = "주소2", example = "서울빌딩 10층")
    private String address2;

    @Schema(description = "배송 상태 (ex: 배송중, 배송완료)", example = "배송중")
    private String status;

    @Schema(description = "택배사명", example = "CJ대한통운")
    private String courierCompany;

    @Schema(description = "운송장 번호", example = "123456789012")
    private String trackingNumber;

    @Schema(description = "배송 메모", example = "경비실에 맡겨주세요.")
    private String deliveryMemo;

    @Schema(description = "배송 출발일자", example = "2025-04-25")
    private String shippedAt;

    @Schema(description = "배송 완료일자", example = "2025-04-27")
    private String deliveredAt;

    @Schema(description = "데이터 생성일시", example = "2025-04-25T12:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "데이터 수정일시", example = "2025-04-26T15:00:00")
    private LocalDateTime updatedAt;

    @Schema(description = "구독 상세 정보 ")
    private SubscribeDetailDto subscribeDetail;
}