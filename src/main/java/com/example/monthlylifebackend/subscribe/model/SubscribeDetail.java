package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.sale.model.Sale;
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

    //한 판매 상품당 가격
    private int price;

    @ManyToOne
    @JoinColumn(name = "sale_idx")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    private Subscribe subscribe;

    @OneToMany(mappedBy = "subscribeDetail")
    private List<RepairRequest> repairRequestList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribeDetail")
    private List<RentalDelivery> rentalDeliveryList = new ArrayList<>();

    @OneToMany(mappedBy = "subscribeDetail")
    private List<ReturnDelivery> returnDeliveryList = new ArrayList<>();





}
