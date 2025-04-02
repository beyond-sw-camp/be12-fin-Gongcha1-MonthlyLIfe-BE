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
// 상품 상태
public class Condition extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    @OneToMany(mappedBy = "condition")
    private List<Item> itemList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sale_has_production_idx")
    private SaleHasProduct saleHasProduct;
}
