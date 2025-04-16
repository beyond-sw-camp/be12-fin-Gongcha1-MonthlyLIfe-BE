package com.example.monthlylifebackend.cart.facade;

import com.example.monthlylifebackend.cart.service.CartService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.service.SaleService;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class CartFacade {


    private final CartService cartService;
    private final SaleService saleService;






    public void addToCart(Long salePriceIdx , User user){
        SalePrice salePrice = saleService.getSalePrice(salePriceIdx);


        cartService.addToCart(user , salePrice);


    }
}
