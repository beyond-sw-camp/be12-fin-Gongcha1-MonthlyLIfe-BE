package com.example.monthlylifebackend.admin.mapper;

import com.example.monthlylifebackend.admin.dto.req.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.res.GetProductItemRes;
import com.example.monthlylifebackend.admin.dto.res.GetProductRes;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.GetProductDetailDto;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.example.monthlylifebackend.product.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    // 재고 수량 수정 요청 DTO → Entity
    Item toEntity(PatchItemCountReq dto);

    // 기존 단일 매핑: 첫 번째 이미지 URL 사용 (상세 DTO 간단 버전)
    @Mapping(source = "product.name", target = "productName")
    @Mapping(source = "product.code", target = "productCode")
    @Mapping(source = "product.description", target = "productDescription")
    @Mapping(source = "product.descriptionImageUrl", target = "productDescriptionImageUrl")
    @Mapping(source = "product.manufacturer", target = "manufacturer")
    @Mapping(source = "images",               target = "productImages")
    GetProductDetailDto toProductDetailDto(Product product, List<ProductImageRes> images);

    // 목록 조회용 매핑: 요약 DTO + 이미지 리스트
    @Mapping(source = "summaryDto.productCode", target = "productCode")
    @Mapping(source = "summaryDto.productName", target = "productName")
    @Mapping(source = "summaryDto.manufacturer", target = "manufacturer")
    @Mapping(source = "summaryDto.totalStockCount", target = "totalStockCount")
    @Mapping(source = "summaryDto.availableStockCount", target = "availableStockCount")
    @Mapping(source = "summaryDto.createdAt", target = "createdAt")
    @Mapping(source = "images", target = "productImages")
    GetProductRes toGetProductRes(GetProductRes summaryDto,
                                  List<ProductImageRes> images);


    // 엔티티 리스트 → 재고 상세 DTO 리스트
    List<ItemDetailDto> toItemDetailDtoList(List<Item> items);


    // 상세 재고 응답 생성: 상품 상세 + 재고 항목 + 이미지 리스트
    default GetProductItemRes toResponse(Product product,
                                         List<ItemDetailDto> items,
                                         List<ProductImageRes> images) {
        return GetProductItemRes.builder()
                .productDetail(
                        toProductDetailDto(product, images)
                )
                .dtoList(items)
                .build();
    }

    // Helper: 첫 번째 이미지 URL 추출
    default String getFirstImageUrl(Product product) {
        return product.getProductImageList() == null || product.getProductImageList().isEmpty()
                ? null
                : product.getProductImageList().get(0).getProductImgUrl();
    }
}

