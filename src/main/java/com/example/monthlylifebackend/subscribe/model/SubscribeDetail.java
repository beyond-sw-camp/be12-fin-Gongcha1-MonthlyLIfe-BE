package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Sale;
import com.example.monthlylifebackend.product.model.SaleHasProduct;
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
public class SubscribeDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "sale_idx")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "sale_has_production_idx")
    private SaleHasProduct saleHasProduct;

    @OneToMany(mappedBy = "subscribe_detail_idx")
    private List<RepairRequest> repairRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribe_detail_idx")
    private List<RentalDelivery> rentalDeliveryList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribe_detail_idx")
    private List<ReturnDelivery> returnDeliveryList = new ArrayList<>();





}
