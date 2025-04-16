package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.req.PostProductImageReq;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.model.ProductImage;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    /** Product 기본 필드 매핑 (이미지는 제외) */
    @Mapping(target = "itemList", ignore = true)
    @Mapping(target = "saleHasProductList", ignore = true)
    Product toEntity(PostProductRegisterReq dto);

    /** ProductImage 리스트 매핑 */
    @IterableMapping(elementTargetType = ProductImage.class)
    List<ProductImage> toProductImageList(List<PostProductImageReq> imageDtos, @Context Product product);

    /** ProductImage 매핑 시 product 참조 포함 */
    @Mapping(target = "idx", ignore = true)
    @Mapping(target = "product", expression = "java(product)")
    ProductImage toProductImage(PostProductImageReq dto, @Context Product product);

    /** Product + 이미지 포함 전체 매핑 */
    default Product toEntityWithImages(PostProductRegisterReq dto) {
        Product product = toEntity(dto);
        if (dto.getProductImages() != null && !dto.getProductImages().isEmpty()) {
            List<ProductImage> imageList = toProductImageList(dto.getProductImages(), product);
            product.getProductImageList().addAll(imageList);
        }
        return product;
    }

    /** 상세 조회용 매핑 (단순 매핑이므로 그대로 유지) */
    GetProductDetailRes toGetProductDetailRes(Product product);

    /** 목록 조회용 매핑 - 기본 필드만 매핑하고 나머지는 후처리 */
    @Mappings({
            @Mapping(target = "condition", ignore = true),
            @Mapping(target = "location", ignore = true),
            @Mapping(target = "count", ignore = true),
            @Mapping(target = "productImages", ignore = true)
    })
    GetProductListRes toGetProductListRes(Product product);

    /** 목록 응답 후처리 - item, imageList 매핑 */
    @AfterMapping
    default void enrichProductListRes(Product product, @MappingTarget GetProductListRes.GetProductListResBuilder res) {
        Item item = product.getItemList().isEmpty() ? null : product.getItemList().get(0);

        res.condition(item != null && item.getCondition() != null ? item.getCondition().getName() : null);
        res.location(item != null && item.getItemLocation() != null ? item.getItemLocation().getName() : null);
        res.count(item != null ? item.getCount() : 0);

        List<PostProductImageReq> imageDtos = product.getProductImageList().stream()
                .map(img -> {
                    PostProductImageReq dto = new PostProductImageReq();
                    try {
                        java.lang.reflect.Field field = PostProductImageReq.class.getDeclaredField("productImgUrl");
                        field.setAccessible(true);
                        field.set(dto, img.getProductImgUrl());
                    } catch (Exception ignored) {}
                    return dto;
                })
                .toList();

        res.productImages(imageDtos);
    }
}
