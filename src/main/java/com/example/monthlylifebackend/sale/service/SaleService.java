package com.example.monthlylifebackend.sale.service;


import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.mapper.SaleMapper;
import com.example.monthlylifebackend.product.model.*;
import com.example.monthlylifebackend.product.repository.*;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleHasProductRepository saleHasProductRepository;
    private final SalePriceRepository salePriceRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SaleMapper saleMapper;

    public Long registerSale(PostSaleRegisterReq dto) {

        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));

        Sale sale = saleMapper.toEntity(dto, category);
        saleRepository.save(sale);

        List<Product> products = productRepository.findAllByCodeIn(
                dto.getSaleProducts().stream()
                        .map(PostSaleRegisterReq.SaleProductInfo::getProductCode)
                        .distinct()
                        .toList()
        );

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getCode, p -> p));

        List<SaleHasProduct> productLinks = dto.getSaleProducts().stream()
                .map(sp -> SaleHasProduct.builder()
                        .sale(sale)
                        .product(productMap.get(sp.getProductCode()))
                        .condition(Condition.builder().idx(sp.getConditionIdx()).build()) // FK만 연결
                        .build())
                .toList();
        saleHasProductRepository.saveAll(productLinks);

        List<SalePrice> prices = saleMapper.toSalePriceList(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(prices);

        return sale.getIdx();
    }


    public List<GetSaleListRes> getSalesByCategory(Long categoryIdx) {
        List<Sale> sales = saleRepository.findByCategoryIdx(categoryIdx);

        return sales.stream().map(sale ->
                GetSaleListRes.builder()
                        .name(sale.getName())
                        .description(sale.getDescription())
                        .productList(
                                sale.getSaleHasProductList().stream().map(shp ->
                                        GetSaleListRes.ProductInfo.builder()
                                                .productCode(shp.getProduct().getCode())
                                                .conditionName(shp.getCondition().getName())
                                                .build()
                                ).toList()
                        )
                        .build()
        ).toList();
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        Sale sale = saleRepository.findByIdxAndCategoryIdx(saleIdx, categoryIdx)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 판매상품이 없음"));

        return GetSaleDetailRes.builder()
                .name(sale.getName())
                .description(sale.getDescription())
                .categoryIdx(categoryIdx)
                .productList(
                        sale.getSaleHasProductList().stream().map(shp ->
                                GetSaleDetailRes.ProductInfo.builder()
                                        .productCode(shp.getProduct().getCode())
                                        .conditionName(shp.getCondition().getName())
                                        .build()
                        ).toList()
                )
                .priceList(
                        sale.getSalePriceList().stream().map(price ->
                                GetSaleDetailRes.PriceInfo.builder()
                                        .period(price.getPeriod())
                                        .price(price.getPrice())
                                        .build()
                        ).toList()
                )
                .build();
    }


}


