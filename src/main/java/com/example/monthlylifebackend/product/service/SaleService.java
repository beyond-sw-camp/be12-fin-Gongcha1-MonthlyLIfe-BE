package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.mapper.SaleMapper;
import com.example.monthlylifebackend.product.model.*;
import com.example.monthlylifebackend.product.repository.*;
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
}


