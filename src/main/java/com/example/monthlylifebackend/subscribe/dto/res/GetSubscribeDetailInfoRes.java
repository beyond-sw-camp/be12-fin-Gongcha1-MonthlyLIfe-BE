package com.example.monthlylifebackend.subscribe.dto.res;

import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubscribeDetailInfoRes {

    @Schema(description = "구독 상세 IDX", example = "1")
    private Long subscribeDetailIdx;

    @Schema(description = "상품 이름", example = "혼라이프 패키지")
    private String salename;

    @Schema(description = "구독 기간 (개월)", example = "3")
    private int period;

    @Schema(description = "상품 가격", example = "29900")
    private int price;

    private String imageUrl;

    private SubscribeStatus status;







}
