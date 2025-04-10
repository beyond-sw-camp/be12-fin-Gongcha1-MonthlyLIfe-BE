package com.example.monthlylifebackend.product.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.dto.req.PostProductRegisterReq;
import com.example.monthlylifebackend.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
@Tag(name = "상품/재고 관리", description = "관리자용 상품 등록, 수정, 재고 처리 API")
public class ProductManagementController {
    private final ProductService productService;

    @Operation(summary = "상품 등록", description = "신규 상품을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Long>> registerProduct(@RequestBody @Valid PostProductRegisterReq dto) {
        // 상품 등록 처리
        BaseResponse<Long> result = BaseResponse.created(productService.registerProduct(dto));
        return ResponseEntity.ok(result);
    }


    @Operation(summary = "상품 수정", description = "기존 상품 정보를 수정합니다.")
    @PostMapping("/update")
    public void updateProduct() {
        // 상품 수정 로직 구현
    }

    @Operation(summary = "상품 삭제", description = "상품을 삭제합니다.")
    @PostMapping("/delete")
    public void deleteProduct() {
        // 상품 삭제 로직 구현
    }

    @Operation(summary = "재고 상태 변경", description = "재고 상태(S/B/C 등)를 변경합니다.")
    @PostMapping("/stock/status")
    public void updateStockStatus() {
        // 재고 상태 변경 로직 구현
    }

    @Operation(summary = "재고 추가", description = "재고를 추가합니다.")
    @PostMapping("/stock/add")
    public void addStock() {
        // 재고 추가 로직 구현
    }

    @Operation(summary = "재고 삭제", description = "재고를 삭제합니다.")
    @PostMapping("/stock/remove")
    public void removeStock() {
        // 재고 삭제 로직 구현
    }
}
