package com.example.monthlylifebackend.product.dto.req;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

import java.util.List;

@Getter
public class PostSaleRegisterReq {
    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Long categoryIdx;

    @NotEmpty
    private List<String> productCodes; // product_code (상품코드 기준)

    @NotEmpty
    private List<SalePriceReq> salePrices;

    @Getter
    public static class SalePriceReq {
        private String period;
        private String price;
    }
}

