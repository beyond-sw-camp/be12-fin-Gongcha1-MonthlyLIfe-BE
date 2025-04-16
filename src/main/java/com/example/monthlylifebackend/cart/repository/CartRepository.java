package com.example.monthlylifebackend.cart.repository;


import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByIdx(Long cartIdx);

    boolean existsByUserAndSalePrice(User user, SalePrice salePrice);


    @Query("""
    SELECT c FROM Cart c
    JOIN FETCH c.salePrice sp
    JOIN FETCH sp.sale s
    JOIN FETCH s.saleHasProductList shp
    WHERE c.user.id = :userId
""")
    List<Cart> findWithSaleByUser(@Param("userId") String userId);



}
