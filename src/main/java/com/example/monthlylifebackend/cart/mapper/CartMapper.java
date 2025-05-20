package com.example.monthlylifebackend.cart.mapper;


import com.example.monthlylifebackend.cart.dto.GetCartListProjection;

import com.example.monthlylifebackend.cart.dto.res.GetCartListRes;
import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.entity.SalePrice;
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



    List<GetCartListRes> toCartDtoListFromProjection(List<GetCartListProjection> projections);
}
