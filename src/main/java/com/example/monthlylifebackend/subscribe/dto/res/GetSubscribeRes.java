package com.example.monthlylifebackend.subscribe.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "구독 정보 응답 DTO")
public class GetSubscribeRes {

    @Schema(description = "구독 ID", example = "123")
    private Long subscribeIdx;

    @Schema(description = "구독 생성일시", example = "2025-04-14T15:36:32.480037")
    private LocalDateTime createdAt;

    @Schema(description = "구독 상세 정보 목록")
    private List<GetSubscribeDetailRes> details;
}
