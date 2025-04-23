package com.example.monthlylifebackend.subscribe.dto.req;

import com.example.monthlylifebackend.subscribe.model.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "수리 신청/분실 신고 요청 DTO")
public class PostRepairOrLostReq {

    @Schema(description = "구독 상세 idx", example = "3", required = true)
    private Long subscribeDetailIdx;

    @Schema(description = "신청 종류 (REPAIR or LOST)", example = "REPAIR", required = true)
    private ReportType type;


    @Schema(description = "신청자 이름", example = "홍길동", required = true)
    private String subscriberName;

    @Schema(description = "신청자 전화번호", example = "010-1234-5678", required = true)
    private String subscriberPhone;

    @Schema(description = "상세 설명", example = "제품이 작동하지 않습니다", required = true)
    private String description;

    private List<String> imageUrls;

}