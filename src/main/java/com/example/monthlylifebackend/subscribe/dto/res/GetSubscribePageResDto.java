package com.example.monthlylifebackend.subscribe.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "구독 페이지 조회 응답 DTO")
public class GetSubscribePageResDto {

    @Schema(description = "상품 이름", example = "혼라이프 패키지")
    private String saleName;

    @Schema(description = "기간(개월 수)", example = "3")
    private int period;

    @Schema(description = "가격", example = "29900")
    private int price;

    @Schema(description = "유저 이름", example = "홍길동")
    private String name;

    @Schema(description = "유저 이메일", example = "user@example.com")
    private String email;
}
