package com.example.monthlylifebackend.payment.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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

    private LocalDateTime scheduledAt;

    private Long price;

    public Payment(String paymentId, Long price, Subscribe subscribe, LocalDateTime scheduledAt) {
        this.paymentId = paymentId;
        this.price = price;
        this.subscribe =subscribe;
        this.scheduledAt = scheduledAt;
        isPaid = false;
    }



    public void paySuccess() {
        isPaid = true;
    }

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    private Subscribe subscribe;
}
