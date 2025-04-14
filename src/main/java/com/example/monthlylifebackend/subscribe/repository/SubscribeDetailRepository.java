package com.example.monthlylifebackend.subscribe.repository;

import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeDetailRepository extends JpaRepository<SubscribeDetail, Long> {
}
