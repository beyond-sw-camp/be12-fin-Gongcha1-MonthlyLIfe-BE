package com.example.monthlylifebackend.sale.dto.req;

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
    public static class SaleProductInfo {
        private String productCode;
        private Long conditionIdx;
    }

    @Getter
    @NoArgsConstructor
    public static class SalePriceReq {
        private String period;
        private String price;
    }
}

