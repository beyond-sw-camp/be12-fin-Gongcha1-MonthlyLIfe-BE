package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import com.example.monthlylifebackend.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RentalDelivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String recipientName;

    private String recipientPhone;

    private String postalCode;

    private String address1;

    private String address2;


    @Builder.Default
    @Enumerated(EnumType.STRING)
    private RentalStatus status = RentalStatus.PREPARING;

    private String courierCompany;

    private String trackingNumber;

    private String deliveryMemo;

    private LocalDateTime shippedAt;

    private LocalDateTime deliveredAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subscribe_detail_idx")
    private SubscribeDetail subscribeDetail;

    public void updatedstatus(RentalStatus status) {
        this.status = status;
    }
}
