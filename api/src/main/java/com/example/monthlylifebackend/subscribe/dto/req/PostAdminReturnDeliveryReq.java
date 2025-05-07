package com.example.monthlylifebackend.subscribe.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "관리자 반납 배송 요청 DTO")
public class PostAdminReturnDeliveryReq {

    @NotBlank(message = "반납 배송 상태는 필수입니다.")
    @Schema(description = "반납 배송 상태", example = "RETURN_ACCEPT")
    private String status;

    @NotBlank(message = "반납 위치는 필수입니다.")
    @Schema(description = "반납 위치", example = "WAREHOUSE")
    private String returnLocation;
}