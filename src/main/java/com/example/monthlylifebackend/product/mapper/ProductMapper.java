package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.ProductImage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(PostProductRegisterReq dto);

    GetProductListRes toGetProductListRes(Product product);
    // 상세 조회용
    GetProductDetailRes toGetProductDetailRes(Product product);

    /**
     * 이미지 포함하여 변환하는 기본 메서드.
     * 기존 toEntity() 매핑을 사용한 후, 요청 DTO에 포함된 이미지 리스트를 Product 엔티티의
     * productImageList에 추가합니다.
     */
    default Product toEntityWithImages(PostProductRegisterReq dto) {
        Product product = toEntity(dto); // 기본 필드 매핑

        if (dto.getProductImages() != null && !dto.getProductImages().isEmpty()) {
            dto.getProductImages().forEach(imageDto -> {
                // 각 이미지 DTO를 ProductImage 엔티티로 변환 후, product 참조를 설정
                ProductImage productImage = ProductImage.builder()
                        .productImgUrl(imageDto.getProductImgUrl())
                        .product(product)
                        .build();
                product.getProductImageList().add(productImage);
            });
        }
        return product;
    }
}
