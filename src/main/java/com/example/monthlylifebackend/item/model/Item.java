package com.example.monthlylifebackend.item.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "재고")
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private int count;

    @ManyToOne
    @JoinColumn(name = "product_code")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "itemlocation_idx")
    private ItemLocation itemLocation;

    @ManyToOne
    @JoinColumn(name = "condition_idx")
    private Condition condition;


    public void updateCount(int count) {
        this.count = count;
    }

    public void reduceOneCount() { this.count--; }
    public void increaseOneCount() { this.count++; }



}

