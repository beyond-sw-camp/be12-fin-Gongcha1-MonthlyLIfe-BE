package com.example.monthlylifebackend.subscribe.dto.req;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "상품 개별 요청 DTO")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostSaleReq {


    @Schema(description = "세일 ID", example = "1")
    private Long sale_idx;

    @Schema(description = "구독 기간 (개월 수)", example = "3")
    private int period;


}
