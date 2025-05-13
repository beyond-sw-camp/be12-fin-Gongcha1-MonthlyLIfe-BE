package com.example.batch.settlement.core.repository;

import com.example.batch.settlement.core.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
