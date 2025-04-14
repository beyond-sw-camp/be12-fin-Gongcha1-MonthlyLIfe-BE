package com.example.monthlylifebackend.sale.repository;


import com.example.monthlylifebackend.sale.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    List<Sale> findByCategoryIdx(Long categoryIdx);
}
