package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "구독 + 렌탈 배송 요청 DTO")
public class PostRentalDeliveryReq {
    @Schema(description = "수령인 이름", example = "홍길동")
    @NotNull
    private String recipientName;

    @Schema(description = "수령인 전화번호", example = "01012345678")
    @NotNull
    @NumberFormat
    private String recipientPhone;

    @Schema(description = "우편번호", example = "12345")
    @NotNull
    private String postalCode;

    @Schema(description = "주소 1", example = "서울특별시 강남구 테헤란로 123")
    @NotNull
    private String address1;

    @Schema(description = "주소 2", example = "101동 202호")
    private String address2;

    @Schema(description = "요청사항", example = "부재 시 집 앞에 놓아주세요")
    private String deliveryMemo;
}
