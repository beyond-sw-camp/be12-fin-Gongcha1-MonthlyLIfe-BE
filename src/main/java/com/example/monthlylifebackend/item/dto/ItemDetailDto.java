package com.example.monthlylifebackend.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailDto {
    private Long itemIdx;
    private String conditionName;
    private String locationName;
    private int itemCount;
    private LocalDateTime createdAt;
}

