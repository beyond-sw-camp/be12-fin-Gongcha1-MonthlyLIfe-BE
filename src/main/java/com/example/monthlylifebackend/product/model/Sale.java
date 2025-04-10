package com.example.monthlylifebackend.product.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
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
public class Sale extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "category_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category category;

    @OneToMany(mappedBy = "sale")
    private List<SaleHasProduct> saleHasProductList = new ArrayList<>();

    @OneToMany(mappedBy = "sale")
    private List<SaleHasUserTag> saleHasUserTagList = new ArrayList<>();

    @OneToMany(mappedBy = "sale")
    private List<SalePrice> salePriceList = new ArrayList<>();

    @OneToMany(mappedBy = "sale")
    private List<SubscribeDetail> subscribeDetailList = new ArrayList<>();

}
