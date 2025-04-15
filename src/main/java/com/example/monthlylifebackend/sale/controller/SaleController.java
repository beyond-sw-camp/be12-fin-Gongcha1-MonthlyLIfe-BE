package com.example.monthlylifebackend.sale.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.Facade.SaleFacade;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.service.SaleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@Tag(name = "판매상품 관리", description = "관리자용 판매상품 등록 및 관리 API")
public class SaleController {

    private final SaleFacade saleFacade;

    @Operation(summary = "판매상품 등록", description = "상품 + 상태 조합 + 기간별 가격을 포함한 판매상품을 등록합니다.")
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<Long>> registerSale(@RequestBody @Valid PostSaleRegisterReq dto) {
        Long saleIdx = saleFacade.registerSale(dto);
        return ResponseEntity.ok(BaseResponse.created(saleIdx));
    }

    @Operation(summary = "카테고리별 판매상품 목록 조회", description = "카테고리별 판매 상품 목록을 조회합니다.")
    @GetMapping("/category/{categoryIdx}")
    public ResponseEntity<BaseResponse<Page<GetSaleListRes>>> getSalesByCategory(
            @PathVariable Long categoryIdx,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size) {
        Page<GetSaleListRes> salesByCategory = saleFacade.getSalesByCategory(categoryIdx,page,size);
        return ResponseEntity.ok(BaseResponse.created(salesByCategory));
    }

    @Operation(summary = "카테고리 내 판매상품 상세 조회", description = "카테고리별로 조회된 판매상품 중 하나를 상세 조회합니다.")
    @GetMapping("/category/{categoryIdx}/{saleIdx}")
    public ResponseEntity<BaseResponse<GetSaleDetailRes>> getSaleDetailInCategory(
            @PathVariable Long categoryIdx,
            @PathVariable Long saleIdx) {

        GetSaleDetailRes saleDetailInCategory = saleFacade.getSaleDetailInCategory(categoryIdx, saleIdx);
        return ResponseEntity.ok(BaseResponse.created(saleDetailInCategory));
    }

    @Operation(summary = "판매 카테고리 목록 조회", description = "판매 등록 시 선택할 수 있는 카테고리 목록을 조회합니다.")
    @GetMapping("/categories")
    public ResponseEntity<BaseResponse<List<GetCategoryRes>>> getSaleCategories() {
        List<GetCategoryRes> categories = saleFacade.getSaleCategoryList();
        return ResponseEntity.ok(BaseResponse.created(categories));
    }


}
