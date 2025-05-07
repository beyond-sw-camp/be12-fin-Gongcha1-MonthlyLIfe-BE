package com.example.monthlylifebackend.product.repository;

import com.example.monthlylifebackend.product.model.Condition;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConditionRepository extends JpaRepository<Condition, Long> {
    Optional<Condition> findFirstByName(String name);
}
