package com.example.monthlylifebackend.product.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
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
    private String code;
    private String description;
    private String name;
    private String manufacturer;
    private String descriptionImageUrl;

    @OneToMany(mappedBy = "product",  cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ProductImage> productImageList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<SaleHasProduct> saleHasProductList = new ArrayList<>();
}
