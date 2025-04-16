package com.example.monthlylifebackend.cart.service;


import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.mapper.CartMapper;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.cart.repository.CartRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.CartHandler;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;




    public void addToCart(User user, SalePrice salePrice) {



        Cart cart =cartMapper.toCartEntity(user,salePrice);

        cartRepository.save(cart);

        if (cartRepository.existsByUserAndSalePrice(user, salePrice)) {
            throw new CartHandler(ErrorStatus._DUPLICATED_CART_ITEM);
        }

    }


    @Transactional
    public void deleteCart(Long cartIdx) {
        cartRepository.deleteByIdx(cartIdx);
    }

    public List<GetCartListRes> getCartList(String userId) {

        List<Cart> cartList = cartRepository.findWithSaleByUser(userId);
        if (cartList == null || cartList.isEmpty()) {
            throw new CartHandler(ErrorStatus._EMPTY_CART);
        }
        List<GetCartListRes> rs =cartMapper.toCartDtoList(cartList);

        return rs;

    }
}
