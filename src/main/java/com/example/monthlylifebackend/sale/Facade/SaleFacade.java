package com.example.monthlylifebackend.sale.Facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SaleFacade {

    private final SaleService saleService;

    public Long registerSale(PostSaleRegisterReq dto) {
        return saleService.registerSale(dto);
    }

    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        return saleService.getSalesByCategory(categoryIdx, page, size);
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        return saleService.getSaleDetailInCategory(categoryIdx, saleIdx);
    }
    public List<GetCategoryRes> getSaleCategoryList() {
        return saleService.getSaleCategoryList();
    }

    public List<GetSaleListRes> getSaleProductList() {
        return saleService.getSaleProductList();
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
}
