package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReturnDelivery extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String recipientName;

    private String recipientPhone;

    private String postalCode;

    private String address1;

    private String address2;

    private String status;

    private String deliveryCol;

    private String courierCompany;

    private String trackingNumber;

    private String deliveryMemo;

    private String shippedAt;

    private String deliveredAt;

    private String deliverycol1;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    private Subscribe subscribe;
}