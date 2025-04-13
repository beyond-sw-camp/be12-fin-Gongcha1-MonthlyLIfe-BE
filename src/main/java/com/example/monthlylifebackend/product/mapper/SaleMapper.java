package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.Sale;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SaleMapper {

    Sale toEntity(PostSaleRegisterReq dto);

}
