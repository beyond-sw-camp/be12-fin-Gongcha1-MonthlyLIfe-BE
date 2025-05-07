package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSubscribeReq {
    @Schema(description = "상품 정보 리스트")
    @NotNull
    private List<PostSaleReq> sales;

    @Schema(description = "배송 정보")
    @NotNull
    private PostRentalDeliveryReq rentalDelivery;

    @Schema(description = "결제 수단")
    @NotNull
    @Min(value = 1, message = "결제수단을 선택해주세요.")
    private Long billingKeyIdx;
}
