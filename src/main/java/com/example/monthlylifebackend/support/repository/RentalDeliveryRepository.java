package com.example.monthlylifebackend.support.repository;

import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalDeliveryRepository extends JpaRepository<RentalDelivery,Long> {
}
