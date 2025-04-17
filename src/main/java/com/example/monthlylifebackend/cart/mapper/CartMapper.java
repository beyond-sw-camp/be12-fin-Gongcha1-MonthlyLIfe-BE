package com.example.monthlylifebackend.cart.mapper;

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


    @Mapping(source = "salePrice.sale.idx", target = "saleidx")
    @Mapping(source = "salePrice.sale.name", target = "salename")
    @Mapping(source = "salePrice.price", target = "price")
    @Mapping(source = "salePrice.period", target = "period")
    GetCartListRes toCartDto(Cart cart);

    List<GetCartListRes> toCartDtoList(List<Cart> cartList);

//    @Mapping(target = "saleidx", source = "salePrice.sale.idx")
//    @Mapping(target = "salename", source = "salePrice.sale.name")
//    @Mapping(target = "period", source = "salePrice.period")
//    @Mapping(target = "price", source = "salePrice.price")
//    @Mapping(target = "productCode", expression = "java(cart.getSalePrice().getSale().getSaleHasProductList().get(0).getProduct().getCode())")
//    GetCartListDto toCartDto(Cart cart);
//
//    List<GetCartListDto> toCartDtoList(List<Cart> carts);

}
