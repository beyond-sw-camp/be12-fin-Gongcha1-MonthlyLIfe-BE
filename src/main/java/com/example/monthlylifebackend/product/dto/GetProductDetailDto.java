package com.example.monthlylifebackend.product.dto;

import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetProductDetailDto {

    private String productName;
    private String productCode;
    private String productDescription;
    private String productDescriptionImageUrl;
    private String manufacturer;
    private List<ProductImageRes> productImages;



}
