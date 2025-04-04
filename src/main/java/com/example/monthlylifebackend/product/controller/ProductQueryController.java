package com.example.monthlylifebackend.product.controller;


import com.example.monthlylifebackend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품 조회", description = "사용자용 상품 조회 API (목록, 검색, 상세 등)")
public class ProductQueryController {

    private final ProductService productService;

    @Operation(summary = "상품 목록 조회", description = "전체 상품 목록을 조회합니다.")
    @GetMapping("/list")
    public void getProductList() {
        // 상품 목록 조회 로직 구현
    }

    @Operation(summary = "카테고리별 상품 목록 조회", description = "카테고리별 상품 목록을 상세 조회합니다.")
    @GetMapping("/category/{categoryId}")
    public void getCategoryDetails(@PathVariable Long categoryId) {
        // 카테고리 목록 상세 조회 로직 구현
    }

    @Operation(summary = "상품 검색", description = "키워드로 상품을 검색합니다.")
    @GetMapping("/search")
    public void searchProducts() {
        // 상품 검색 로직 구현
    }

    @Operation(summary = "상품 상세 조회", description = "상품 ID로 상세 정보를 조회합니다.")
    @GetMapping("/{id}")
    public void getProductDetail(@PathVariable("id") Long productId) {
        // 상품 상세 조회 로직 구현
    }
}
