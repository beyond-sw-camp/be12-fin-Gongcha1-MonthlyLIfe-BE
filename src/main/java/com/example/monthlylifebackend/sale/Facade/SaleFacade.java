package com.example.monthlylifebackend.sale.Facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.service.SaleService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SaleFacade {

    private final SaleService saleService;

    public Long registerSale(PostSaleRegisterReq dto) {
        return saleService.registerSale(dto);
    }

    public List<GetSaleListRes> getSalesByCategory(Long categoryIdx) {
        return saleService.getSalesByCategory(categoryIdx);
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        return saleService.getSaleDetailInCategory(categoryIdx, saleIdx);
    }
    public List<GetCategoryRes> getSaleCategoryList() {
        return saleService.getSaleCategoryList();
    }
}
