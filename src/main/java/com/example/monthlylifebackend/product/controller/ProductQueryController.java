package com.example.monthlylifebackend.product.controller;


import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.dto.res.GetProductDetailRes;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.product.facade.ProductFacade;
import com.example.monthlylifebackend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@Tag(name = "상품 조회", description = "사용자용 상품 조회 API (목록, 검색, 상세 등)")
public class ProductQueryController {

    private final ProductFacade productFacade;


    @Operation(summary = "상품 목록 조회", description = "상품 목록을 조회합니다.")
    @GetMapping("/list")
    public ResponseEntity<BaseResponse<List<GetProductListRes>>> getProductList() {
        List<GetProductListRes> productList = productFacade.getProductList();
        return ResponseEntity.ok(BaseResponse.created(productList));
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
    public ResponseEntity<BaseResponse<GetProductDetailRes>> getProductDetail(@PathVariable("id") Long productId) {
        GetProductDetailRes detail = productFacade.getProductDetail(productId);
        return ResponseEntity.ok(BaseResponse.created(detail));
    }
}
