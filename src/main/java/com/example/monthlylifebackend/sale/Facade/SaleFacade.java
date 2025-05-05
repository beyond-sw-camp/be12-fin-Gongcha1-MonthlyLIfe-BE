package com.example.monthlylifebackend.sale.Facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.*;
import com.example.monthlylifebackend.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Slice;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SaleFacade {

    private final SaleService saleService;

    public Long registerSale(PostSaleRegisterReq dto) {
        return saleService.registerSale(dto);
    }

//    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
//        return saleService.getSalesByCategory(categoryIdx, page, size);
//    }

    public Slice<GetSaleListSliceRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        return saleService.getSalesByCategory(categoryIdx, page, size);
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        return saleService.getSaleDetailInCategory(categoryIdx, saleIdx);
    }

    public List<GetCategoryRes> getSaleCategoryList() {
        return saleService.getSaleCategoryList();
    }

    public Slice<GetSaleListRes> getSaleProductList(
            int page,
            int size
    ) {
        return saleService.getSaleProductList(
                page,
                size);
    }

    public Page<GetSaleListRes> getSaleSearch(
            Long categoryIdx,
            int page,
            int size,
            String keyword,
            String grade
    ) {
        return saleService.getSaleSearch(
                categoryIdx, page, size, keyword, grade
        );
    }

    public Long updateSale(Long saleIdx, PatchSaleReq dto) {
        return saleService.updateSale(saleIdx, dto);
    }

    public void deleteSale(Long saleIdx) {
        saleService.deleteSale(saleIdx);
    }

    public List<BestSaleListRes> getBestSales(int limit) {
        return saleService.getBestSales(limit);
    }

    public Slice<PackageSaleRes> getPackageSales(int page, int size) {
        return saleService.getPackageSales(page, size);
    }

    public Page<GetSaleListRes> searchByKeyword(String keyword, int page, int size) {
        return saleService.searchByKeyword(keyword, page, size);
    }

    public List<BestSaleListRes> getCategoryBestSales(int limit, Long categoryIdx) {
        return saleService.getCategoryBestSales(limit, categoryIdx);
    }

}
