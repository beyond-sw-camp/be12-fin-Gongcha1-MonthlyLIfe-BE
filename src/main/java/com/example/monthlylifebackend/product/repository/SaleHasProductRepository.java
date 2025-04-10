package com.example.monthlylifebackend.product.repository;

import com.example.monthlylifebackend.product.model.SaleHasProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaleHasProductRepository extends JpaRepository<SaleHasProduct, Long> {


    SaleHasProduct findByIdx(Long salehasproductIdx);
}
