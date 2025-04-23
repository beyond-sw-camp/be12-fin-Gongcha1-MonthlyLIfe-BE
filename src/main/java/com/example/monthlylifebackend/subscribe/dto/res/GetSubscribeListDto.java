package com.example.monthlylifebackend.subscribe.dto.res;

import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetSubscribeListDto {


    private  Long saleidx;
    private Long subscribeDetailIdx;
    private String salename;

    private LocalDateTime startAt;
    private LocalDateTime endAt;
    @Schema(description = "구독 기간 (개월)", example = "3")
    private int period;

    @Schema(description = "상품 가격", example = "29900")
    private int price;

    @Schema(description = "구독 상태", example = "SUBSCRIBING")
    private SubscribeStatus status;

    @Schema(description = "상품 이미지 URL", example = "https://image.server.com/product/1.jpg")
    private String productImgurl;
}
