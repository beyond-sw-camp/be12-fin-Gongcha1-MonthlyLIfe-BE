package com.example.monthlylifebackend.cart.mapper;

import com.example.monthlylifebackend.cart.dto.GetCartListProjection;
import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface CartMapper {

    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "saleprice", target = "salePrice")
    Cart toCartEntity(User user, SalePrice saleprice);



//
//    @Mapping(target = "saleidx", source = "salePrice.sale.idx")
//    @Mapping(target = "salename", source = "salePrice.sale.name")
//    @Mapping(target = "period", source = "salePrice.period")
//    @Mapping(target = "price", source = "salePrice.price")
//    @Mapping(target = "productCode", expression = "java(cart.getSalePrice().getSale().getSaleHasProductList().get(0).getProduct().getCode())")
//    @Mapping(target = "productImgurl", expression = "java(cart.getSalePrice().getSale().getSaleHasProductList().get(0).getProduct().getProductImageList().isEmpty() ? null : cart.getSalePrice().getSale().getSaleHasProductList().get(0).getProduct().getProductImageList().get(0).getProductImgUrl())")
//
//    GetCartListDto toCartDto(Cart cart);

//    List<GetCartListDto> toCartDtoList(List<Cart> carts);


    List<GetCartListRes> toCartDtoListFromProjection(List<GetCartListProjection> projections);
}
