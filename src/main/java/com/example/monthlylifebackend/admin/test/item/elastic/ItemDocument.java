package com.example.monthlylifebackend.admin.test.item.elastic;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ItemDocument {
    @JsonProperty("product_code")
    private String productCode;

    @JsonProperty("product_name")
    private String productName;

    private String manufacturer;

    @JsonProperty("total_stock_count")
    private Long totalStockCount;

    @JsonProperty("available_stock_count")
    private Long availableStockCount;

    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @JsonProperty("product_images")
    private List<ProductImage> productImages;

    @Getter
    @Setter
    public static class ProductImage {
        @JsonProperty("productImgUrl")
        private String productImgUrl;
    }
}
