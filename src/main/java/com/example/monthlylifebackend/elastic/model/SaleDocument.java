package com.example.monthlylifebackend.elastic.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "sale")
@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SaleDocument {
    @Id
    private Long idx;

    private String name;

    private String description;

    private Long categoryIdx;
}
