package com.example.monthlylifebackend.cart.controller;


import com.example.monthlylifebackend.cart.facade.CartFacade;

import com.example.monthlylifebackend.sale.model.SalePrice;


import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartFacade cartFacade;

    // 유저 인증된 상태로 가정
    @PostMapping("/{salePriceIdx}")
    public void addToCart(@PathVariable Long salePriceIdx/*,@AuthenticationPrincipal User user*/) {

        User user = User.builder().id("1").build();

        cartFacade.addToCart(salePriceIdx , user);
//        cartService.addToCart(user, salePrice);
    }

//    @DeleteMapping("/{salePriceId}")
//    public void removeFromCart(@PathVariable Long salePriceId, @RequestAttribute("user") User user) {
//        SalePrice salePrice = salePriceRepository.findById(salePriceId)
//                .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));
//        cartService.removeFromCart(user, salePrice);
//    }
}