package com.example.monthlylifebackend.sale.dto.req;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
public class PostSaleRegisterReq {
    private String name;
    private String description;
    private Long categoryIdx;

    private List<SaleProductInfo> saleProducts;

    private List<SalePriceReq> salePrices;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaleProductInfo {
        private String productCode;
        private Long conditionIdx;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SalePriceReq {
        private int period;
        private int price;
    }
}

