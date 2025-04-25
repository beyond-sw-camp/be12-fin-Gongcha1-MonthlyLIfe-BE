package com.example.monthlylifebackend.admin.dto.request;

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
@Schema(description = "재고 수량 변경 요청 DTO")
public class PatchItemCountReq {

    @NotNull(message = "변경할 재고 수량은 필수입니다.")
    @Min(value = 0, message = "재고 수량은 0 이상이어야 합니다.")
    @Schema(description = "변경할 재고 수량", example = "50")
    private int count;

    public Integer getCount() {
        return count;
    }
}
