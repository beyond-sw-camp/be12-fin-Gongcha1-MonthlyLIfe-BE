package com.example.monthlylifebackend.cart.facade;

import com.example.monthlylifebackend.cart.dto.GetCartListDto;
import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.service.CartService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.service.ProductService;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.service.SaleService;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class CartFacade {


    private final CartService cartService;
    private final SaleService saleService;
    private final ProductService productService;





    public void addToCart(Long salePriceIdx , User user){
        SalePrice salePrice = saleService.getSalePrice(salePriceIdx);
        cartService.addToCart(user , salePrice);
    }

    public void deleteCart(Long cartIdx) {
        cartService.deleteCart(cartIdx);
    }

//    public List<GetCartListRes> getCartList(User user) {
    public List<GetCartListDto> getCartList(User user) {

        List<GetCartListDto> getcartListDto = cartService.getCartList(user.getId());
//        List<T> codelist ;
        productService.getProductImg(getcartListDto);
//        Mapper
        return cartService.getCartList(user.getId());
    }
}
