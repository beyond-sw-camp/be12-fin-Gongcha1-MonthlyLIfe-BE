package com.example.monthlylifebackend.product.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDetailDto {

    private String productName;
    private String productCode;
    private String productDescription;
    private String manufacturer;
    private String productImageUrl;



}
