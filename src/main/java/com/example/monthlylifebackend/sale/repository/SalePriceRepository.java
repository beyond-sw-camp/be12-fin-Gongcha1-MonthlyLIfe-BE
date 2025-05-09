package com.example.monthlylifebackend.sale.repository;

import com.example.monthlylifebackend.chatV2.api.model.res.GetAiSubscribeDetailRes;
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



    @Query(value = """
    SELECT 
        sp.idx AS salePriceIdx,
        s.idx AS saleIdx,
        sp.period,
        sp.price,
        p.name
    FROM product p
    JOIN sale_has_product shp ON shp.product_code = p.code
    JOIN sale s ON shp.sale_idx = s.idx
    JOIN sale_price sp ON sp.sale_idx = s.idx
    JOIN `condition` c ON shp.condition_idx = c.idx
    WHERE p.name LIKE %:itemName%
      AND sp.period = :period
      AND c.name = :condition
    LIMIT 1
    """, nativeQuery = true)
    Optional<GetAiSubscribeDetailRes> findSalePriceInfo(
            @Param("itemName") String itemName,
            @Param("period") Integer period,
            @Param("condition") String condition
    );




}
