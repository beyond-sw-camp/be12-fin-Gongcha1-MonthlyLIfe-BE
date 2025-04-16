package com.example.monthlylifebackend.cart.repository;


import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByIdx(Long cartIdx);

    boolean existsByUserAndSalePrice(User user, SalePrice salePrice);
}
