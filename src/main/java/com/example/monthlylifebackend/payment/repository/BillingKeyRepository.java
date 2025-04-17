package com.example.monthlylifebackend.payment.repository;

import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BillingKeyRepository extends JpaRepository<BillingKey, Long> {
    Page<BillingKey> findAllByUser(User user, Pageable pageable);
}
