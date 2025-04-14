package com.example.monthlylifebackend.sale.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Condition;
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
public class SaleHasUser extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    @ManyToOne
    @JoinColumn(name = "condition_idx")
    private Condition condition;
}
