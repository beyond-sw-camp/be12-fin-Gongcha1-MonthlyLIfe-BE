package com.example.batch.settlement.core.domain;

import com.example.batch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SubscribeDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private LocalDateTime startAt;

    private LocalDateTime endAt;

    private int price;

    private Integer period;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_idx")
    private Sale sale;

    @ManyToOne
    @JoinColumn(name = "subscribe_idx")
    private Subscribe subscribe;

    @Version
    private Long version;

    private String status;
}
