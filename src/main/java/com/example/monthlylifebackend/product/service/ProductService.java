package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.item.model.Item;
import com.example.monthlylifebackend.item.model.ItemLocation;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ConditionRepository;
import com.example.monthlylifebackend.product.repository.ItemLocationRepository;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ConditionRepository conditionRepository;
    private final ItemLocationRepository itemLocationRepository;
    private final ItemRepository itemRepository;

    public String registerProduct(PostProductRegisterReq dto) {
        // Product 생성
        Product product = productMapper.toEntityWithImages(dto);
        productRepository.save(product);

        // Condition 조회
        Condition condition = conditionRepository.findByName(dto.getCondition())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품 상태 등급입니다: " + dto.getCondition()));

        // ItemLocation 조회
        ItemLocation location = itemLocationRepository.findByName(dto.getLocation())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 위치입니다: " + dto.getLocation()));

        // Item 생성
        Item item = Item.builder()
                .product(product)
                .condition(condition)
                .itemLocation(location)
                .count(1)         // 기본 재고 수량 1개로 설정 (필요 시 수정)
                .build();

        itemRepository.save(item);

        return product.getCode();
    }

    // 상품 목록 조회
    public List<GetProductListRes> getProductList() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toGetProductListRes)
                .toList();
    }

    // 상품 상세 조회
    public GetProductDetailRes getProductDetail(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("상품을 찾을 수 없습니다."));
        return productMapper.toGetProductDetailRes(product);
    }

}
