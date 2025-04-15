package com.example.monthlylifebackend.subscribe.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "구독 상세 응답 DTO")
public class GetSubscribeDetailRes {

        @Schema(description = "구독 상세 ID", example = "101")
        private Long idx;

        @Schema(description = "상품 이름", example = "혼라이프 패키지")
        private String name;

        @Schema(description = "구독 기간 (개월)", example = "3")
        private int period;

        @Schema(description = "상품 가격", example = "29900")
        private int price;
}
