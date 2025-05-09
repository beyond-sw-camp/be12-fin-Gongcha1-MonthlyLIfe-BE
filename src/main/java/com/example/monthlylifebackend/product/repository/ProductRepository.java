package com.example.monthlylifebackend.product.repository;


import com.example.monthlylifebackend.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByCodeIn(List<String> codes);

//    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
//    List<Product> findByNameContaining(String name);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Optional<Product> findFirstByNameContaining(String name);

    List<Product> findTop3ByNameContaining(String itemName);
}
