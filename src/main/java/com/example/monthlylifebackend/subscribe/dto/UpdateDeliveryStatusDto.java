package com.example.monthlylifebackend.subscribe.dto;


import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDeliveryStatusDto {
    private Long detailIdx;
    private ReturnDeliveryStatus status;
    private LocalDateTime updatedAt;
    // + 생성자/빌더/게터/세터
}