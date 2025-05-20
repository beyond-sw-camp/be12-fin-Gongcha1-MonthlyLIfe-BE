package com.example.monthlylifebackend.admin.test.sale;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaleDocument {

    @JsonProperty("idx")
    private Long idx;

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("price")
    private Integer price;

    @JsonProperty("period")
    private Integer period;

    @JsonProperty("category_idx")
    private Long categoryIdx;

    @JsonProperty("image_url")
    private String imageUrl;

    @JsonProperty("condition_name")
    private String conditionName;
}

