package com.example.monthlylifebackend.sale.repository;

import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleHasProductRepository extends JpaRepository<SaleHasProduct, Long> {


    SaleHasProduct findByIdx(Long salehasproductIdx);
}
