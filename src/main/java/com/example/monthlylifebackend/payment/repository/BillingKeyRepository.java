package com.example.monthlylifebackend.payment.repository;

import com.example.monthlylifebackend.payment.model.BillingKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long> {
}
