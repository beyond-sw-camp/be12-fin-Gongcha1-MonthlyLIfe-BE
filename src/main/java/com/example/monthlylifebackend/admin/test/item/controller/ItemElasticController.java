package com.example.monthlylifebackend.admin.test.item.controller;

import com.example.monthlylifebackend.admin.dto.res.GetProductRes;
import com.example.monthlylifebackend.admin.test.item.service.ItemElasticSearchService;
import com.example.monthlylifebackend.common.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ItemElasticController {

    private final ItemElasticSearchService itemElasticSearchService;

    @Operation(summary = "관리자 전체 페이징 재고 조회", description = "판매 아이템의 재고 관리페이지를 페이징 처리하여 조회합니다.")
    @GetMapping("/product-by-page")
    public BaseResponse<Page<GetProductRes>> getAllItemsByPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) String manufacturer,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws IOException {
        Page<GetProductRes> dto = itemElasticSearchService.searchItems(productName, manufacturer, startDate, endDate, page, size);
        return BaseResponse.onSuccess(dto);
    }
}
