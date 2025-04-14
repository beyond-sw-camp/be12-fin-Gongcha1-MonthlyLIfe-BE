package com.example.monthlylifebackend.subscribe.dto.req;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequestDto {
    private Long sale_idx;
    private int period;
    private int price;
}
