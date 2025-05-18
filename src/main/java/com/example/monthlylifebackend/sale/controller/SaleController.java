package com.example.monthlylifebackend.sale.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.Facade.SaleFacade;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.product.dto.res.GetProductListRes;
import com.example.monthlylifebackend.sale.dto.res.*;
import com.example.monthlylifebackend.sale.service.SaleService;
import com.example.monthlylifebackend.sale.dto.res.BestSaleListRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.dto.res.PackageSaleRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Getter
@RestController
@RequestMapping("/sale")
@RequiredArgsConstructor
@Tag(name = "판매상품 관리", description = "관리자용 판매상품 등록 및 관리 API")
public class SaleController {

        private final SaleFacade saleFacade;

        @Operation(summary = "판매상품 등록", description = "상품 + 상태 조합 + 기간별 가격을 포함한 판매상품을 등록합니다.")
        @PostMapping("/create")
        public BaseResponse<Long> registerSale(@RequestBody @Valid PostSaleRegisterReq dto) {
            Long saleIdx = saleFacade.registerSale(dto);
            return BaseResponse.onSuccess(saleIdx);
        }

        @Operation(summary = "판매 상품 목록 조회", description = "판매 상품 목록을 조회합니다.")
        @GetMapping("/list")
        public BaseResponse<Slice<GetSaleListRes>> getProductList(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "6") int size
        ) {
            Slice<GetSaleListRes> saleProductList = saleFacade.getSaleProductList(page, size);
            return BaseResponse.onSuccess(saleProductList);
        }

        //    @Operation(summary = "판매 상품 목록 조회", description = "판매 상품 목록을 조회합니다.")
    //    @GetMapping("/list")
    //    public BaseResponse<Slice<GetSaleWeatherRes>> getProductList(
    //            @RequestParam(defaultValue = "0") int page,
    //            @RequestParam(defaultValue = "6") int size
    //    ) {
    //        Slice<GetSaleWeatherRes> saleProductList = saleFacade.getWeatherSales(page, size);
    //        return BaseResponse.onSuccess(saleProductList);
    //    }
        //    @Operation(summary = "카테고리별 판매상품 목록 조회", description = "카테고리별 판매 상품 목록을 조회합니다.")
    //    @GetMapping("/category/{categoryIdx}")
    //    public BaseResponse<Page<GetSaleListRes>> getSalesByCategory(
    //            @PathVariable Long categoryIdx,
    //            @RequestParam(defaultValue = "0") int page,
    //            @RequestParam(defaultValue = "3") int size) {
    //        Page<GetSaleListRes> salesByCategory = saleFacade.getSalesByCategory(categoryIdx, page, size);
    //        return BaseResponse.onSuccess(salesByCategory);
    //    }
        @Operation(summary = "카테고리별 판매상품 목록 조회", description = "카테고리별 판매 상품 목록을 조회합니다.")
        @GetMapping("/category/{categoryIdx}")
        public BaseResponse<Slice<GetSaleListSliceRes>> getSalesByCategory(
                @PathVariable Long categoryIdx,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "3") int size) {
            Slice<GetSaleListSliceRes> salesByCategory = saleFacade.getSalesByCategory(categoryIdx, page, size);
            return BaseResponse.onSuccess(salesByCategory);
        }

        @Operation(summary = "카테고리 내 판매상품 상세 조회", description = "카테고리별로 조회된 판매상품 중 하나를 상세 조회합니다.")
        @GetMapping("/category/{categoryIdx}/{saleIdx}")
        public BaseResponse<GetSaleDetailRes> getSaleDetailInCategory(
                @PathVariable Long categoryIdx,
                @PathVariable Long saleIdx) {

            GetSaleDetailRes saleDetailInCategory = saleFacade.getSaleDetailInCategory(categoryIdx, saleIdx);
            return BaseResponse.onSuccess(saleDetailInCategory);
        }

        @Operation(summary = "판매 카테고리 목록 조회", description = "판매 등록 시 선택할 수 있는 카테고리 목록을 조회합니다.")
        @GetMapping("/categories")
        public BaseResponse<List<GetCategoryRes>> getSaleCategories() {
            List<GetCategoryRes> categories = saleFacade.getSaleCategoryList();
            return BaseResponse.onSuccess(categories);
        }

        @Operation(summary = "판매 상품 카테고리별 검색", description = "판매 상품의 카테고리별 상품의 검색 기능")
        @GetMapping("/search")
        public BaseResponse<Slice<GetSaleListSliceRes>> searchSales(
                @RequestParam Long categoryIdx,
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "3") int size,
                @RequestParam(required = false) String keyword,
                @RequestParam(required = false) String grade
        ) {
            Slice<GetSaleListSliceRes> result = saleFacade.getSaleSearch(
                    categoryIdx, page, size, keyword, grade
            );
            return BaseResponse.onSuccess(result);
        }

        @Operation(summary = "판매 상품 수정", description = "기존 판매 상품의 정보를 수정합니다.")
        @PostMapping("/{saleIdx}/update")
        public BaseResponse<Long> updateSale(
                @PathVariable Long saleIdx,
                @RequestBody @Valid PatchSaleReq dto
        ) {
            Long updatedIdx = saleFacade.updateSale(saleIdx, dto);
            return BaseResponse.onSuccess(updatedIdx);
        }

        @Operation(summary = "판매 상품 삭제", description = "특정 판매 상품을 삭제합니다.")
        @PostMapping("/{saleIdx}/delete")
        public BaseResponse<Void> deleteSale(@PathVariable Long saleIdx) {
            saleFacade.deleteSale(saleIdx);
            return BaseResponse.onSuccess(null);
        }

        @Operation(summary = "Best 상품 조회", description = "구독 수 기준 상위 N개의 상품을 조회합니다.")
        @GetMapping("/best")
        public BaseResponse<List<BestSaleListRes>> getBestSales(
                @RequestParam(defaultValue = "5") int limit
        ) {
            List<BestSaleListRes> bestList = saleFacade.getBestSales(limit);
            return BaseResponse.onSuccess(bestList);
        }

    @Operation(summary = "패키지 상품 조회", description = "패키지 형태로 구성된 판매상품을 페이지네이션으로 조회합니다.")
        @GetMapping("/packages")
        public BaseResponse<Slice<PackageSaleRes>> getPackageSales(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "6") int size
        ) {
            Slice<PackageSaleRes> pkgs = saleFacade.getPackageSales(page, size);
            return BaseResponse.onSuccess(pkgs);
        }

        @Operation(summary = "판매 상품 키워드 검색", description = "검색어에 매칭되는 모든 판매상품을 페이징 조회합니다."
        )
        @GetMapping("/searchall")
        public BaseResponse<Slice<GetSaleListRes>> searchByKeyword(
                @RequestParam(defaultValue = "0") int page,
                @RequestParam(defaultValue = "6") int size,
                @RequestParam String keyword
        ) {
            Slice<GetSaleListRes> result = saleFacade.searchByKeyword(keyword, page, size);
            return BaseResponse.onSuccess(result);
        }

        // 새로 추가할 카테고리별 Best 조회
        @Operation(summary = "카테고리별 Best 상품 조회",
                description = "특정 카테고리의 구독 수 기준 상위 N개 상품을 조회합니다.")
        @GetMapping("/{categoryIdx}/best")
        public BaseResponse<List<GetBestSaleRes>> getCategoryBestSales(
                @PathVariable Long categoryIdx,
                @RequestParam(defaultValue = "5") int limit
        ) {
            List<GetBestSaleRes> bestList = saleFacade.getCategoryBestSales(limit, categoryIdx);
            return BaseResponse.onSuccess(bestList);
        }

        @Operation(summary = "신규 상품 조회", description = "최신 등록 순으로 상위 N개 판매상품을 조회합니다.")
        @GetMapping("/new-arrivals")
        public BaseResponse<List<NewSaleListRes>> getNewArrivals(
                @RequestParam(defaultValue = "10") int limit
        ) {
            List<NewSaleListRes> list = saleFacade.getNewArrivals(limit);
            return BaseResponse.onSuccess(list);
        }


        @Operation(summary = "전체 Best 상품 조회", description = "구독 수 기준으로 전체 판매상품 중 상위 N개를 조회합니다.")
        @GetMapping("/best/all")
        public BaseResponse<List<GetBestSaleRes>> getAllBestSales(
                @RequestParam(defaultValue = "8") int limit
        ) {
            List<GetBestSaleRes> list = saleFacade.getAllBestSales(limit);
            return BaseResponse.onSuccess(list);
        }
}
