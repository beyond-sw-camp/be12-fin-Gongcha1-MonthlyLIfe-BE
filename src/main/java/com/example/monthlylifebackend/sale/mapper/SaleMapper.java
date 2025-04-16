package com.example.monthlylifebackend.sale.mapper;

import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    @Mapping(target = "idx", ignore = true)
    @Mapping(target = "category", expression = "java(category)")
    @Mapping(target = "saleHasProductList", ignore = true)
    @Mapping(target = "saleHasUserTagList", ignore = true)
    @Mapping(target = "salePriceList", ignore = true)
    @Mapping(target = "subscribeDetailList", ignore = true)
    Sale toEntity(PostSaleRegisterReq dto, @Context Category category);

    @Mapping(target = "idx", ignore = true)
    @Mapping(target = "sale", expression = "java(sale)")
    SalePrice toSalePrice(PostSaleRegisterReq.SalePriceReq dto, @Context Sale sale);

    @IterableMapping(elementTargetType = SalePrice.class)
    List<SalePrice> toSalePriceList(List<PostSaleRegisterReq.SalePriceReq> list, @Context Sale sale);
}

