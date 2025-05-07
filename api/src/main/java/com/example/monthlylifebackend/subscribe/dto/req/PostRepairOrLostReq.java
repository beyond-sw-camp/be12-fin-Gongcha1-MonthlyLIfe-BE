package com.example.monthlylifebackend.subscribe.dto.req;

import com.example.monthlylifebackend.subscribe.model.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "수리 신청/분실 신고 요청 DTO")
public class PostRepairOrLostReq {

    @Schema(description = "구독 상세 idx", example = "3", required = true)
    @NotNull(message = "구독 상세 idx는 필수입니다.")
    private Long subscribeDetailIdx;

    @Schema(description = "신청 종류 (REPAIR or LOST)", example = "REPAIR", required = true)
    @NotNull(message = "신청 종류는 필수입니다.")
    private ReportType type;

    @Schema(description = "신청자 이름", example = "홍길동", required = true)
    @NotBlank(message = "신청자 이름은 필수입니다.")
    private String subscriberName;

    @Schema(description = "신청자 전화번호", example = "010-1234-5678", required = true)
    @NotBlank(message = "신청자 전화번호는 필수입니다.")
    private String subscriberPhone;

    @Schema(description = "상세 설명", example = "제품이 작동하지 않습니다.", required = true)
    @NotBlank(message = "상세 설명은 필수입니다.")
    private String description;

    @Schema(description = "신청 관련 이미지 URL 목록", example = "[\"https://example.com/image1.png\", \"https://example.com/image2.png\"]", required = false)
    private List<String> imageUrls;

}