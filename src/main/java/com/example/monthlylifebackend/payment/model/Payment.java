package com.example.monthlylifebackend.payment.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Payment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String paymentId;

    private boolean isPaid;

    private Long price;

    public Payment(String paymentId, Long price) {
        this.paymentId = paymentId;
        this.price = price;
        isPaid = false;
    }



    public void paySuccess() {
        isPaid = true;
    }

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    private Subscribe subscribe;
}
