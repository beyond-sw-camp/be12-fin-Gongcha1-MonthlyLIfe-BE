package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "구독 연장 요청 DTO")

public class PostExtendRequest {

    @Schema(description = "연장 개월수", example = "3")
    private int extentPeriod;
}
