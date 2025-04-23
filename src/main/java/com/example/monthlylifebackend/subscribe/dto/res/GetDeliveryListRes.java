package com.example.monthlylifebackend.subscribe.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
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
@Schema(description = "구독 요약 정보")
public class GetDeliveryListRes {

    @Schema(description = "구독 ID", example = "1")
    private Long subscribeIdx;

    @Schema(description = "회원 이름", example = "홍길동")
    private String userName;

    @Schema(description = "구독 가격", example = "30000")
    private Long subscribePrice;

    @Schema(description = "배송 상태", example = "배송중")
    private String deliveryStatus;

    @Schema(description = "회원 전화번호", example = "010-1234-5678")
    private String userPhone;

    @Schema(description = "구독 상세 생성일", example = "2024-04-11T10:20:30")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime subscribeDetailCreatedAt;
}