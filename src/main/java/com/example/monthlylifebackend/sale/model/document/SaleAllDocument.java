//package com.example.monthlylifebackend.sale.model.document;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//
////GetSaleListRes와 대응
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Document(indexName = "sale-all")
//public class SaleAllDocument {
//
//    @Id
//    private Long idx;
//
//    private String name;
//
//    private String description;
//
//    @Field(name = "categoryIdx", type = FieldType.Long)
//    private Long categoryIdx;
//
//    @Field(name = "conditionName", type = FieldType.Text)
//    private String conditionName;
//
//    private Integer price;
//
//    private Integer period;
//
//    @Field(name = "updated_at", type = FieldType.Date)
//    private LocalDateTime updatedAt;
//
//    @Field(type = FieldType.Nested)
//    private List<PriceItem> priceList;
//
//    @Field(type = FieldType.Nested)
//    private List<ProductItem> productList;
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class PriceItem {
//        private Long period;
//        private Long price;
//    }
//
//    @Getter
//    @Setter
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class ProductItem {
//        @Field(name = "product_code")
//        private String productCode;
//
//        @Field(name = "condition_name")
//        private String conditionName;
//
//        @Field(name = "image_urls")
//        private List<String> imageUrls;
//    }
//}
