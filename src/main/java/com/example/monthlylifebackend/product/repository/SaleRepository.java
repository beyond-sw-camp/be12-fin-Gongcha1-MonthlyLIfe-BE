package com.example.monthlylifebackend.product.repository;


import com.example.monthlylifebackend.sale.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {


    Optional<Sale> findByIdx(Long idx);
}
