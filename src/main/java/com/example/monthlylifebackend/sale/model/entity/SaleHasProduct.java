package com.example.monthlylifebackend.sale.model.entity;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SaleHasProduct extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "sale_idx")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "condition_idx")
    private Condition condition;

    public SaleHasProduct(Long idx, Sale sale, Product product, Condition condition) {
        this.idx = idx;
        this.sale = sale;
        this.product = product;
        this.condition = condition;
    }
}
