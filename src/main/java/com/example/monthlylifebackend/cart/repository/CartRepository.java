package com.example.monthlylifebackend.cart.repository;


import com.example.monthlylifebackend.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    void deleteByIdx(Long cartIdx);
}
