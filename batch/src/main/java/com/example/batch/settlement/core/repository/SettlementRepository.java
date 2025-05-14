package com.example.batch.settlement.core.repository;

import com.example.batch.settlement.core.domain.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
    Optional<Settlement> findBySettlementDate(LocalDate date);
}
