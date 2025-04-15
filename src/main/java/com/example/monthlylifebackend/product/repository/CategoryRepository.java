package com.example.monthlylifebackend.product.repository;

import com.example.monthlylifebackend.product.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
