package com.example.monthlylifebackend.subscribe.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetSubscribePageResDto {

    private String saleName;

    private int period;
    private int price;


    private String name;
    private String email;




}
