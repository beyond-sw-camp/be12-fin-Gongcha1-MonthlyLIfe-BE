package com.example.monthlylifebackend.sale.repository;

import com.example.monthlylifebackend.sale.model.SalePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalePriceRepository extends JpaRepository<SalePrice, Long> {
    Optional<SalePrice> findBySaleIdxAndPeriod(Long saleIdx, int period);

    Optional<SalePrice> findByIdx(Long salePriceIdx);

    @Modifying
    @Query("delete from SalePrice sp where sp.sale.idx = :saleIdx")
    void deleteAllBySaleIdx(@Param("saleIdx") Long saleIdx);
}
