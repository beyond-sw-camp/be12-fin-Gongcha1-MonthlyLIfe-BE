package com.example.monthlylifebackend.product.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.sale.model.entity.SaleHasProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`condition`")
// 상품 상태
public class Condition extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @OneToMany(mappedBy = "condition")
    private List<Item> itemList = new ArrayList<>();

    @OneToMany(mappedBy = "condition")
    @Builder.Default
    private List<SaleHasProduct> saleHasProductList = new ArrayList<>();
}
