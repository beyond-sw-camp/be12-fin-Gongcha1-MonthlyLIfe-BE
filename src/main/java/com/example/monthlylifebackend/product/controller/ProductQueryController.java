package com.example.monthlylifebackend.product.controller;


import com.example.monthlylifebackend.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class ProductQueryController {


    private final ProductService productService ;

    //todo 상품 목록 조회 우선순위 1
    @GetMapping("/")
    public void getProductList() {
        // 상품 목록 조회 로직 구현
    }

    //todo 카테고리 목록 상세 조회 우선순위 1
    @GetMapping("/")
    public void getCategoryDetails() {
        // 카테고리 목록 상세 조회 로직 구현
    }

    //todo 상품 검색 우선순위 2
    @GetMapping("/")
    public void searchProducts() {
        // 상품 검색 로직 구현
    }

    //todo 상품 상세 조회 우선순위 1
    @GetMapping("/")
    public void getProductDetail(@PathVariable("id") Long productId) {
        // 상품 상세 조회 로직 구현
    }




}
