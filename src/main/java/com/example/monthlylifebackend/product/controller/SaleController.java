package com.example.monthlylifebackend.product.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.service.SaleService;
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
@RequestMapping("/sale")
@RequiredArgsConstructor
@Tag(name = "판매상품 관리", description = "관리자용 판매상품 등록 및 관리 API")
public class SaleController {

    private final SaleService saleService;

    @Operation(summary = "판매상품 등록", description = "상품 + 상태 조합 + 기간별 가격을 포함한 판매상품을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Long>> registerSale(@RequestBody @Valid PostSaleRegisterReq dto) {
        Long saleIdx = saleService.registerSale(dto);
        return ResponseEntity.ok(BaseResponse.created(saleIdx));
    }
}
