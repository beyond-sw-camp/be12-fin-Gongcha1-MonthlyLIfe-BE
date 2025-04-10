package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.ProductImage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(PostProductRegisterReq dto);

    GetProductListRes toGetProductListRes(Product product);
}
