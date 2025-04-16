package com.example.monthlylifebackend.cart.service;


import com.example.monthlylifebackend.cart.mapper.CartMapper;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.cart.repository.CartRepository;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;




    public void addToCart(User user, SalePrice salePrice) {



        Cart cart =cartMapper.toCartEntity(user,salePrice);

        cartRepository.save(cart);

        if (cartRepository.existsByUserAndSalePrice(user, salePrice)) {
            throw new CustomException("이미 장바구니에 담긴 상품입니다.");
        }

    }


    @Transactional
    public void deleteCart(Long cartIdx) {
        cartRepository.deleteByIdx(cartIdx);
    }
}
