package com.example.monthlylifebackend.cart.controller;


import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.facade.CartFacade;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.sale.model.SalePrice;


import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
@Tag(name = "Cart", description = "장바구니 관련 API")

public class CartController {
    private final CartFacade cartFacade;

    // 유저 인증된 상태로 가정
    @Operation(summary = "장바구니에 상품 추가", description = "특정 상품을 장바구니에 추가합니다.")
    @PostMapping("/{salePriceIdx}")
    public BaseResponse addToCart(@PathVariable Long salePriceIdx,
                                  @AuthenticationPrincipal @Valid @NotNull User user) {
        cartFacade.addToCart(salePriceIdx , user);
        return BaseResponse.onSuccess(null);
    }







    @Operation(summary = "장바구니에서 상품 삭제", description = "장바구니에서 특정 항목을 삭제합니다.")
    @PostMapping("/{cartIdx}/delete")
    public BaseResponse deleteCart(@PathVariable Long cartIdx) {
        cartFacade.deleteCart(cartIdx);

        return BaseResponse.onSuccess(null);

    }


    @Operation(summary = "장바구니 조회", description = "장바구니를 조회합니다.")
    @GetMapping("/list")
    public BaseResponse<List<GetCartListRes>> getCartList(@AuthenticationPrincipal @Valid @NotNull User user){
        List<GetCartListRes> rs =cartFacade.getCartList(user);

        return BaseResponse.onSuccess(rs);
    }

}