package com.example.monthlylifebackend.sale.repository;

import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleHasProductRepository extends JpaRepository<SaleHasProduct, Long> {


    SaleHasProduct findByIdx(Long salehasproductIdx);

    @Modifying
    @Query("delete from SaleHasProduct shp where shp.sale.idx = :saleIdx")
    void deleteAllBySaleIdx(@Param("saleIdx") Long saleIdx);
}
