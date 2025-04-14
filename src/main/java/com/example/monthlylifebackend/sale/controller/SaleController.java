package com.example.monthlylifebackend.sale.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @Operation(summary = "카테고리별 판매상품 목록 조회")
    // GET /sale/category/1
    @GetMapping("/category/{categoryIdx}")
    public ResponseEntity<BaseResponse<List<GetSaleListRes>>> getSalesByCategory(
            @PathVariable Long categoryIdx) {
        List<GetSaleListRes> salesByCategory = saleService.getSalesByCategory(categoryIdx);
        return ResponseEntity.ok(BaseResponse.created(salesByCategory));
    }


}
