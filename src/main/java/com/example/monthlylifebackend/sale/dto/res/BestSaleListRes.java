package com.example.monthlylifebackend.sale.dto.res;

import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BestSaleListRes {
    private Long saleIdx;
    private String name;
    private String description;
    private Long categoryIdx;
    private List<SaleProductInfo> productList;
    private List<SalePriceInfo>  priceList;

    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class SaleProductInfo {
        private String productCode;
        private Long   conditionIdx;
    }

    @Getter @NoArgsConstructor @AllArgsConstructor
    public static class SalePriceInfo {
        private int  period;
        private int  price;
    }
    private Long subscribeCount;
}
