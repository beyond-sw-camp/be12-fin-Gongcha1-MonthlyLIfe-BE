package com.example.monthlylifebackend.cart.service;


import com.example.monthlylifebackend.cart.dto.GetCartListProjection;
import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.mapper.CartMapper;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.cart.repository.CartRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.CartHandler;
import com.example.monthlylifebackend.sale.model.entity.SalePrice;
import com.example.monthlylifebackend.user.model.User;
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


        if (cartRepository.existsByUserAndSalePrice(user, salePrice)) {
            throw new CartHandler(ErrorStatus._DUPLICATED_CART_ITEM);
        }

        Cart cart =cartMapper.toCartEntity(user,salePrice);

        cartRepository.save(cart);


    }


    @Transactional

    public void deleteCart(Long cartIdx) {
        cartRepository.deleteByIdx(cartIdx);
    }

//public List<GetCartListDto> getCartList(String userId) {
//    List<Cart> cartList = cartRepository.findWithSaleByUser(userId);
//
//    if (cartList == null || cartList.isEmpty()) {
//        throw new CartHandler(ErrorStatus._EMPTY_CART);
//    }
//
//    return cartMapper.toCartDtoList(cartList);
//}

    public List<GetCartListRes> getCartList(String userId) {
        List<GetCartListProjection> projections = cartRepository.findCartListProjection(userId);

        if (projections == null || projections.isEmpty()) {
            throw new CartHandler(ErrorStatus._EMPTY_CART);
        }
        return cartMapper.toCartDtoListFromProjection(projections);

    }

    @Transactional
    public void deleteCartAfterSubscribe(User user, List<SalePrice> salePriceList) {
        for(SalePrice salePrice : salePriceList) {
            cartRepository.deleteCartBySalePriceAndUser(salePrice, user);
        }
    }


}
