package com.example.monthlylifebackend.admin.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 대시보드 카드뷰")
public class GetAdminHomeCardRes {

    @Schema(description = "총 사용자 수", example = "245")
    private Long userCount;
    @Schema(description = "이번달 예상 매출", example = "239857980")
    private Long revenue;

}
