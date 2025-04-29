package com.example.monthlylifebackend.sale.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PackageSaleRes {
    private Long saleIdx;
    private String name;
    private String description;
    private Long categoryIdx;
    private List<ProductInfo> productList;
    private List<PriceInfo>   priceList;

    @Getter @AllArgsConstructor @NoArgsConstructor
    public static class ProductInfo {
        private String productCode;
        private String imageUrl;
        private String conditionName;
    }

    @Getter @AllArgsConstructor
    @NoArgsConstructor
    public static class PriceInfo {
        private int period;
        private int price;
    }
}
