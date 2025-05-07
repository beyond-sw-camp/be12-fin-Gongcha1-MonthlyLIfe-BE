package com.example.monthlylifebackend.payment.repository;

import com.example.monthlylifebackend.payment.model.Settlement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SettlementRepository extends JpaRepository<Settlement, Long> {
}
