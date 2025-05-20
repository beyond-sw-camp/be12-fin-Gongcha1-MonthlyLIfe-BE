package com.example.monthlylifebackend.admin.test.sale;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListSliceRes;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sale")
public class SaleElasticController {
    private final SaleElasticSearchService saleElasticSearchService;
    @Operation(summary = "판매 상품 카테고리별 검색", description = "판매 상품의 카테고리별 상품의 검색 기능")
    @GetMapping("/search")
    public BaseResponse<Slice<GetSaleListSliceRes>> searchSales(
            @RequestParam Long categoryIdx,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String grade
    ) {
        try{
        Slice<GetSaleListSliceRes> result = saleElasticSearchService.searchSaleByKeyword(
                categoryIdx, page, size, keyword, grade
        );
        return BaseResponse.onSuccess(result);
        }
        catch (Exception e){
            throw new RuntimeException("Elasticsearch 통신 실패", e);

        }
    }

}
