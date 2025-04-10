package com.example.monthlylifebackend.product.service;


import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.mapper.ProductMapper;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
 
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public Long registerProduct(PostProductRegisterReq dto) {
         return productRepository.save(productMapper.toEntity(dto)).getIdx();
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
