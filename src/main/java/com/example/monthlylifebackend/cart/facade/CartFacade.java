package com.example.monthlylifebackend.cart.facade;

import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.service.CartService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.service.ProductService;
import com.example.monthlylifebackend.sale.model.entity.SalePrice;
import com.example.monthlylifebackend.sale.service.SaleService;
import com.example.monthlylifebackend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class CartFacade {


    private final CartService cartService;
    private final SaleService saleService;
    private final ProductService productService;




    @Transactional
    public void addToCart(Long salePriceIdx , User user){
        SalePrice salePrice = saleService.getSalePrice(salePriceIdx);
        cartService.addToCart(user , salePrice);
    }

    public void deleteCart(Long cartIdx) {
        cartService.deleteCart(cartIdx);
    }

    public List<GetCartListRes> getCartList(User user) {

        List<GetCartListRes> rs = cartService.getCartList(user.getId());

        return rs;
    }
}
