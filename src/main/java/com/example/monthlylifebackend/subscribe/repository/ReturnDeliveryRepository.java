package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.model.ReturnDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReturnDeliveryRepository  extends JpaRepository<ReturnDelivery,Long> {
}
