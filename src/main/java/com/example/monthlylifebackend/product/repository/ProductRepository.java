package com.example.monthlylifebackend.product.repository;


import com.example.monthlylifebackend.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByCodeIn(List<String> codes);
}
