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
    private Integer idx;

    private String recipientName;

    private String recipientPhone;

    private String postalCode;

    private String address1;

    private String address2;

    private String status;

    private String deliverycol;

    private String courierCompany;

    private String trackingNumber;

    private String deliveryMemo;

    private String shippedAt;

    private String deliveredAt;

    private String deliveryCol1;

    private Long subscribeIdx;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subscribeDetail_idx")
    private SubscribeDetail subscribeDetail;
}
