package com.example.monthlylifebackend.sale.mapper;

import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SaleMapper {

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

//    GetSaleListRes toGetSaleProductListRes(Sale sale);
    default GetSaleListRes toGetSaleProductListRes(Sale sale) {
        List<GetSaleListRes.ProductInfo> productList = sale.getSaleHasProductList().stream()
                .filter(shp -> shp.getProduct() != null && shp.getCondition() != null)
                .map(shp -> new GetSaleListRes.ProductInfo(
                        shp.getProduct().getCode(),
                        shp.getCondition().getName()
                ))
                .toList();

        List<GetSaleDetailRes.PriceInfo> priceList = sale.getSalePriceList().stream()
                .map(price -> new GetSaleDetailRes.PriceInfo(
                        price.getPeriod(),
                        price.getPrice()
                ))
                .toList();

        return new GetSaleListRes(
                sale.getName(),
                sale.getDescription(),
                productList,
                priceList
        );
    }
}

