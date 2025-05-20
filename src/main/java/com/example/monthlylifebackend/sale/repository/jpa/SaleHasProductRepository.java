package com.example.monthlylifebackend.sale.repository.jpa;

import com.example.monthlylifebackend.sale.model.entity.Sale;
import com.example.monthlylifebackend.sale.model.entity.SaleHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleHasProductRepository extends JpaRepository<SaleHasProduct, Long> {


    SaleHasProduct findByIdx(Long salehasproductIdx);

    @Modifying
    @Query("delete from SaleHasProduct shp where shp.sale.idx = :saleIdx")
    void deleteAllBySaleIdx(@Param("saleIdx") Long saleIdx);

    List<SaleHasProduct> findBySale(Sale sale);

}
