package com.example.batch.settlement.core.repository;

import com.example.batch.settlement.core.domain.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @EntityGraph(attributePaths = {"subscribe", "subscribe.user"})
    Page<Payment> findAllByScheduledAtGreaterThanEqualAndScheduledAtLessThan(LocalDateTime start, LocalDateTime end, Pageable pageable);
}
