package com.example.batch.settlement.core.domain;


import com.example.batch.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Settlement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private LocalDate settlementDate;

    private Long totalSalesAmount;
    private Long totalRefundAmount;
    private Long netAmount;
    private int transactionCount;
    private LocalDateTime settledAt;

    public Settlement(LocalDate settlementDate, Long totalSalesAmount) {
        this.settlementDate = settlementDate;
        this.totalSalesAmount = totalSalesAmount;
    }

    public void addAmount(Long amount) {
        this.totalSalesAmount += amount;
    }
}