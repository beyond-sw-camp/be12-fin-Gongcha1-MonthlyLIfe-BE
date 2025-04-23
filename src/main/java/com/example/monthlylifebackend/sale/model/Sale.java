package com.example.monthlylifebackend.sale.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Category;
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

    @OneToMany(mappedBy = "sale", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SaleHasProduct> saleHasProductList = new ArrayList<>();

    @OneToMany(mappedBy = "sale")
    private List<SaleHasUserTag> saleHasUserTagList = new ArrayList<>();

    @OneToMany(mappedBy = "sale", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<SalePrice> salePriceList = new ArrayList<>();

    @OneToMany(mappedBy = "sale")
    private List<SubscribeDetail> subscribeDetailList = new ArrayList<>();

    public void changeCategory(Category newCategory) {
        this.category = newCategory;
    }
    // name 변경용
    public void changeName(String name) {
        if (name != null) {
            this.name = name;
        }
    }

    // description 변경용
    public void changeDescription(String description) {
        if (description != null) {
            this.description = description;
        }
    }

}
