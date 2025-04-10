package com.example.monthlylifebackend.support.repository;

import com.example.monthlylifebackend.subscribe.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
