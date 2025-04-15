package com.example.monthlylifebackend.admin.mapper;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductItemRes;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.GetProductDetailDto;
import com.example.monthlylifebackend.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    Item toEntity(PatchItemCountReq dto);

    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "product.description", target = "productDescription")
    @Mapping(source = "product.manufacturer", target = "manufacturer")
    @Mapping(expression = "java(getFirstImageUrl(product))", target = "productImageUrl")
    GetProductDetailDto toProductDetailDto(Product product);

    List<ItemDetailDto> toItemDetailDtoList(List<Item> items);

    default String getFirstImageUrl(Product product) {
        return product.getProductImageList().isEmpty() ? null : product.getProductImageList().get(0).getProductImgUrl();
    }

    default GetProductItemRes toResponse(Product product, List<ItemDetailDto> items) {
        return GetProductItemRes.builder()
                .productDetail(toProductDetailDto(product))
                .dtoList(items)
                .build();
    }
}

