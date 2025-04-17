package com.example.monthlylifebackend.product.mapper;

import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import com.example.monthlylifebackend.product.model.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    /** 등록용: 이미지 제외하고 기본 필드만 매핑 */
    @Mapping(target = "itemList", ignore = true)
    @Mapping(target = "saleHasProductList", ignore = true)
    @Mapping(target = "productImageList", ignore = true)
    Product toEntity(PostProductRegisterReq dto);

    /** 상세 조회용 매핑 */
    GetProductDetailRes toGetProductDetailRes(Product product);

    /** 목록 조회용 매핑 - 기본 필드만 매핑, 나머지는 @AfterMapping 처리 **/
    @Mappings({
            @Mapping(target = "condition",     ignore = true),
            @Mapping(target = "location",      ignore = true),
            @Mapping(target = "count",         ignore = true),
            @Mapping(target = "productImages", ignore = true)
    })
    GetProductListRes toGetProductListRes(Product product);

    /** 목록 응답 후처리 – item, imageList 매핑 **/
    @AfterMapping
    default void enrichProductListRes(Product product,
                                      @MappingTarget GetProductListRes.GetProductListResBuilder res) {
        Item item = product.getItemList().isEmpty() ? null : product.getItemList().get(0);
        res.condition(item != null && item.getCondition() != null
                ? item.getCondition().getName() : null);
        res.location(item != null && item.getItemLocation() != null
                ? item.getItemLocation().getName() : null);
        res.count(item != null ? item.getCount() : 0);

        List<ProductImageRes> imgs = product.getProductImageList().stream()
                .map(pi -> new ProductImageRes(pi.getProductImgUrl()))
                .toList();
        res.productImages(imgs);
    }

}
