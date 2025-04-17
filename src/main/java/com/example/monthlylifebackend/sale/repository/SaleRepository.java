package com.example.monthlylifebackend.sale.repository;


import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.salePriceList WHERE s.idx = :idx")
    Optional<Sale> findWithSalePricesByIdx(@Param("idx") Long idx);

    @Query("""
    SELECT sp FROM SalePrice sp
    WHERE sp.sale.idx = :saleIdx AND sp.period = :period
""")
    Optional<SalePrice> findBySaleIdxAndPeriod(@Param("saleIdx") Long saleIdx, @Param("period") int period);

    Page<Sale> findByCategoryIdx(Long categoryIdx, Pageable pageable);
    Optional<Sale> findByIdxAndCategoryIdx(Long saleIdx, Long categoryIdx);
}
