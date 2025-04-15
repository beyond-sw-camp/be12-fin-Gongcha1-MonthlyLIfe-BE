package com.example.monthlylifebackend.product.repository;

import com.example.monthlylifebackend.item.model.ItemLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemLocationRepository extends JpaRepository<ItemLocation, Long> {
    Optional<ItemLocation> findByName(String name);
}