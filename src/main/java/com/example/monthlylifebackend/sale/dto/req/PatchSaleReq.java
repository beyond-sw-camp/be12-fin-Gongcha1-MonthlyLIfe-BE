package com.example.monthlylifebackend.sale.dto.req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PatchSaleReq {
    private Long categoryIdx;
    private String name;
    private String description;
    private List<SalePriceReq> salePrices;


    @Getter
    @Setter
    @NoArgsConstructor
    public static class SalePriceReq {
        private Integer period;
        private Long price;
    }
}
