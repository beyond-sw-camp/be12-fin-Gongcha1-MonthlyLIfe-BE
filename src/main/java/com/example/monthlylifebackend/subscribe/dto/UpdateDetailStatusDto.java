package com.example.monthlylifebackend.subscribe.dto;


import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UpdateDetailStatusDto {
    private Long detailIdx;
    private SubscribeStatus status;
    // + 생성자/빌더/게터/세터
}