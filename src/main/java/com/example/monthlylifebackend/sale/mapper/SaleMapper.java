package com.example.monthlylifebackend.sale.mapper;

import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SaleMapper {




    // --- 등록용 Entity 매핑 (변경 없음) ---

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

    // --- patch 용 개별 요소 매핑 추가 ---
    @Named("toSalePriceForPatch")
    @Mapping(target = "idx", ignore = true)
    @Mapping(target = "sale", expression = "java(sale)")
    @Mapping(source = "period", target = "period")
    @Mapping(source = "price",  target = "price")
    SalePrice toSalePriceForPatch(PatchSaleReq.SalePriceReq dto, @Context Sale sale);


    @IterableMapping(qualifiedByName = "toSalePriceForPatch")
    List<SalePrice> toSalePriceListForPatch(
            List<PatchSaleReq.SalePriceReq> list,
            @Context Sale sale
    );


    // --- 조회용 DTO 매핑 ---

    /**
     * Sale → List 응답 DTO
     */
    @Mapping(source = "idx", target = "idx")             // 사실 같은 이름은 생략해도 자동 매핑됨
    @Mapping(source = "category.idx", target = "categoryIdx")
    @Mapping(source = "saleHasProductList", target = "productList")
    @Mapping(source = "salePriceList", target = "priceList")
    GetSaleListRes toGetSaleListRes(Sale sale);

    /**
     * Sale → Detail 응답 DTO
     */
    @Mapping(source = "idx", target = "idx")             // ID 필드는 DTO 에 idx 로 선언되어 있으므로
    @Mapping(source = "category.idx", target = "categoryIdx")
    @Mapping(source = "saleHasProductList", target = "productList")
    @Mapping(source = "salePriceList", target = "priceList")
    @Mapping(
            target = "descriptionImageUrls",
            // Collectors.toList() 대신에 java.util.stream.Collectors.toList() 를 직접 사용
            expression = "java(sale.getSaleHasProductList().stream()\n" +
                    "    .map(shp -> shp.getProduct().getDescriptionImageUrl())\n" +
                    "    .collect(java.util.stream.Collectors.toList()))"
    )
    GetSaleDetailRes toGetSaleDetailRes(Sale sale);


    // --- 내부 리스트 요소 매핑: SaleHasProduct → ProductInfo ---

//    @IterableMapping(elementTargetType = GetSaleListRes.ProductInfo.class)
//    @Mapping(source = "product.code", target = "productCode")
//    @Mapping(source = "condition.name", target = "conditionName")
//    List<GetSaleListRes.ProductInfo> mapProductInfoList(List<SaleHasProduct> list);
//
//    @Mapping(source = "product.code", target = "productCode")
//    @Mapping(source = "condition.name", target = "conditionName")
//    GetSaleListRes.ProductInfo toProductInfo(SaleHasProduct shp);
//
//
//    // --- 내부 리스트 요소 매핑: SalePrice → PriceInfo ---
//
//    @IterableMapping(elementTargetType = GetSaleListRes.PriceInfo.class)
//    List<GetSaleListRes.PriceInfo> mapPriceInfoList(List<SalePrice> list);
//
//    @Mapping(source = "period", target = "period")
//    @Mapping(source = "price", target = "price")
//    GetSaleListRes.PriceInfo toPriceInfo(SalePrice price);

    // Category → GetCategoryRes
    @Mapping(source = "idx", target = "idx")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "iconUrl", target = "iconUrl")
    @Mapping(source = "parent.idx", target = "parentIdx")
    GetCategoryRes toCategoryRes(Category category);


    // --- Detail용 리스트 요소 매핑: SaleHasProduct → GetSaleDetailRes.ProductInfo ---

//    @IterableMapping(elementTargetType = GetSaleDetailRes.ProductInfo.class)
//    @Mapping(source = "product.code", target = "productCode")
//    @Mapping(source = "condition.name", target = "conditionName")
//    List<GetSaleDetailRes.ProductInfo> mapDetailProductInfoList(List<SaleHasProduct> list);
//
//    @Mapping(source = "product.code", target = "productCode")
//    @Mapping(source = "condition.name", target = "conditionName")
//    GetSaleDetailRes.ProductInfo toDetailProductInfo(SaleHasProduct shp);


    // --- Detail용 리스트 요소 매핑: SalePrice → GetSaleDetailRes.PriceInfo ---
    default GetSaleDetailRes.ProductInfo toDetailProductInfo(SaleHasProduct shp) {
        return GetSaleDetailRes.ProductInfo.builder()
                .conditionName(shp.getCondition().getName())
                .imageUrls(
                        shp.getProduct()
                                .getProductImageList()
                                .stream()
                                .map(pi -> pi.getProductImgUrl())
                                .collect(Collectors.toList())
                )
                .build();
    }



    @IterableMapping(elementTargetType = GetSaleDetailRes.PriceInfo.class)
    List<GetSaleDetailRes.PriceInfo> mapDetailPriceInfoList(List<SalePrice> list);

    @Mapping(source = "idx", target = "salePriceIdx")
    @Mapping(source = "period", target = "period")
    @Mapping(source = "price", target = "price")
    GetSaleDetailRes.PriceInfo toDetailPriceInfo(SalePrice price);

    /**
     * PatchSaleReq → 기존 Sale 엔티티 업데이트
     * - null 값은 건너뛰도록 설정해서, DTO에 없는 필드는 그대로 둡니다.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "idx", ignore = true)                 // 기본 키는 건드리지 않음
    @Mapping(target = "category", ignore = true)
    void updateSaleFromDto(
            PatchSaleReq dto,
            @MappingTarget Sale sale
    );

}
