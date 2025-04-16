package com.example.monthlylifebackend.cart.mapper;

import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")

public interface CartMapper {

    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "user", target = "user")
    @Mapping(source = "saleprice", target = "salePrice")
    Cart toCartEntity(User user, SalePrice saleprice);




}
