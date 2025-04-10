package com.example.monthlylifebackend.product.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
// 판매 상품 엔티티
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String description;
    private String code;
    private String name;
    private String Manufacturer;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<SaleHasProduct> saleHasProductList = new ArrayList<>();
}
