package com.example.monthlylifebackend.product.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "카테고리 응답 DTO")
public class GetCategoryRes {

    @Schema(description = "카테고리 IDX")
    private Long idx;

    @Schema(description = "카테고리 이름")
    private String name;

    @Schema(description = "카테고리 아이콘 URL")
    private String iconUrl;

    @Schema(description = "상위 카테고리 IDX (없으면 null)")
    private Long parentIdx;
}
