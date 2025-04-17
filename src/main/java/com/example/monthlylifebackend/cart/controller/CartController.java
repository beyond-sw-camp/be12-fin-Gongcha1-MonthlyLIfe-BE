package com.example.monthlylifebackend.cart.controller;


import com.example.monthlylifebackend.cart.facade.CartFacade;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.sale.model.SalePrice;


import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart", description = "장바구니 관련 API")

public class CartController {
    private final CartFacade cartFacade;

    // 유저 인증된 상태로 가정
    @Operation(summary = "장바구니에 상품 추가", description = "특정 상품을 장바구니에 추가합니다.")
    @PostMapping("/{salePriceIdx}")
    public BaseResponse addToCart(@PathVariable Long salePriceIdx/*,@AuthenticationPrincipal User user*/) {

        User user = User.builder().id("1").build();

        cartFacade.addToCart(salePriceIdx , user);
        return BaseResponse.onSuccess(null);
    }


    @Operation(summary = "장바구니에서 상품 삭제", description = "장바구니에서 특정 항목을 삭제합니다.")
    @PostMapping("/{cartIdx}/delete")
    public BaseResponse deleteCart(@PathVariable Long cartIdx) {
        cartFacade.deleteCart(cartIdx);

        return BaseResponse.onSuccess(null);

    }

}