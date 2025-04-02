package com.example.monthlylifebackend.exam;

import com.example.monthlylifebackend.common.example.ExampleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<ExampleEntity, Long> {
}
