package com.example.monthlylifebackend.sale.mapper;

import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    default Sale toEntity(PostSaleRegisterReq dto, @Context Category category) {
        return Sale.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .category(category)
                .build();
    }

    default List<SalePrice> toSalePriceList(List<PostSaleRegisterReq.SalePriceReq> reqList, Sale sale) {
        if (reqList == null) return new ArrayList<>();
        return reqList.stream().map(p ->
                SalePrice.builder()
                        .sale(sale)
                        .period(p.getPeriod())
                        .price(p.getPrice())
                        .build()
        ).toList();
    }
}


