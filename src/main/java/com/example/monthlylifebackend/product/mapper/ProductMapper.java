package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.req.PostProductImageReq;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.ProductImage;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product toEntity(PostProductRegisterReq dto);

    default GetProductListRes toGetProductListRes(Product product) {
// ItemList가 비어있지 않으면 첫 번째 Item을 기준으로 condition, location, count 추출
        Item item = product.getItemList().isEmpty() ? null : product.getItemList().get(0);

        return GetProductListRes.builder()
                .name(product.getName())
                .code(product.getCode())
                .manufacturer(product.getManufacturer())
                .description(product.getDescription())
                .condition(item != null && item.getCondition() != null ? item.getCondition().getName() : null)
                .location(item != null && item.getItemLocation() != null ? item.getItemLocation().getName() : null)
                .count(item != null ? item.getCount() : 0)
                .productImages(
                        product.getProductImageList().stream()
                                .map(img -> {
                                    PostProductImageReq dto = new PostProductImageReq();
                                    // reflection 기반 setter 없으면 생성자 추가해도 됨
                                    try {
                                        java.lang.reflect.Field field = PostProductImageReq.class.getDeclaredField("productImgUrl");
                                        field.setAccessible(true);
                                        field.set(dto, img.getProductImgUrl());
                                    } catch (Exception ignored) {}
                                    return dto;
                                })
                                .toList()
                )
                .build();
    }
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
