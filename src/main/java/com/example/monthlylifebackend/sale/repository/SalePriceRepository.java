package com.example.monthlylifebackend.sale.repository;

import com.example.monthlylifebackend.sale.model.SalePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalePriceRepository extends JpaRepository<SalePrice, Long> {
    Optional<SalePrice> findBySaleIdxAndPeriod(Long saleIdx, int period);
}
