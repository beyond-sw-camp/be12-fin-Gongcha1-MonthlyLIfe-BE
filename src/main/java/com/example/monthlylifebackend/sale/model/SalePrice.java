package com.example.monthlylifebackend.sale.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.subscribe.model.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SalePrice extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private int period;
    private int price;

    @OneToMany(mappedBy = "salePrice")
    private List<Cart> cartList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "sale_idx")
    private Sale sale;
}
