//package com.example.monthlylifebackend.sale.model.document;
//
//import lombok.*;
//import org.springframework.data.annotation.Id;
//import org.springframework.data.elasticsearch.annotations.Document;
//import org.springframework.data.elasticsearch.annotations.Field;
//import org.springframework.data.elasticsearch.annotations.FieldType;
//
//import java.time.LocalDateTime;
//
//
////GetSaleListSliceRes와 대응
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//@Document(indexName = "sale")
//public class SaleDocument {
//
//    @Id
//    private Long idx;
//
//    private String name;
//
//    private String description;
//
//    @Field(name = "category_idx", type = FieldType.Long)
//    private Long categoryIdx;
//
//    private String imageUrl;
//
//    @Field(name = "condition_name")
//    private String conditionName;
//
//    private Integer price;
//
//    private Integer period;
//
//    @Field(name = "updated_at", type = FieldType.Date)
//    private LocalDateTime updatedAt;
//
//
//}
