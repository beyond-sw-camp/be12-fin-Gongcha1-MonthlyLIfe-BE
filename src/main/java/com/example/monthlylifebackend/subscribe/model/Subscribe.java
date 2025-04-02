package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Item;
import com.example.monthlylifebackend.support.model.RepairImage;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "상품 구독")
public class Subscribe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private int price;

    private String period;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_idx")
    private Item item;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> paymentList;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReturnDelivery> returnDeliveryList;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairRequest> repairRequestList;

    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RentalDelivery> rentalDeliveryListl;
}

