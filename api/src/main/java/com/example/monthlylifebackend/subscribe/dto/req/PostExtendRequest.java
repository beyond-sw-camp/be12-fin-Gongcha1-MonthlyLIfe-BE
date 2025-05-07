package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "연장 개월수는 필수입니다.")
    @Min(value = 1, message = "연장 개월수는 최소 1개월 이상이어야 합니다.")
    @Schema(description = "연장 개월수", example = "3")
    private Integer extentPeriod;
}
