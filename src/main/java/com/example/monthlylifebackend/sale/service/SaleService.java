package com.example.monthlylifebackend.sale.service;


import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.mapper.SaleMapper;
import com.example.monthlylifebackend.product.model.*;
import com.example.monthlylifebackend.product.repository.*;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    private final SaleRepository saleRepository;
    private final SaleHasProductRepository saleHasProductRepository;
    private final SalePriceRepository salePriceRepository;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ConditionRepository conditionRepository;
    private final SaleMapper saleMapper;

    public Long registerSale(PostSaleRegisterReq dto) {

        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new RuntimeException("카테고리 없음"));

        Sale sale = saleMapper.toEntity(dto, category);
        saleRepository.save(sale);

        List<Product> products = productRepository.findAllByCodeIn(
                dto.getSaleProducts().stream()
                        .map(PostSaleRegisterReq.SaleProductInfo::getProductCode)
                        .distinct()
                        .toList()
        );

        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getCode, p -> p));

        List<SaleHasProduct> productLinks = dto.getSaleProducts().stream()
                .map(sp -> {
                    Product product = productMap.get(sp.getProductCode());
                    if (product == null) {
                        throw new RuntimeException("등록되지 않은 상품 코드입니다: " + sp.getProductCode());
                    }

                    Condition condition = conditionRepository.findById(sp.getConditionIdx())
                            .orElseThrow(() -> new RuntimeException("Condition 없음"));

                    return new SaleHasProduct(null, sale, product, condition);
                })
                .toList();
        saleHasProductRepository.saveAll(productLinks);

        List<SalePrice> prices = saleMapper.toSalePriceList(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(prices);

        return sale.getIdx();
    }

    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Sale> sales = saleRepository.findByCategoryIdx(categoryIdx, pageRequest);

        return sales.map(sale -> {
            List<GetSaleListRes.ProductInfo> productList = sale.getSaleHasProductList().stream()
                    .filter(shp -> shp.getProduct() != null && shp.getCondition() != null)
                    .map(shp -> new GetSaleListRes.ProductInfo(
                            shp.getProduct().getCode(),
                            shp.getCondition().getName()
                    ))
                    .toList();


            List<GetSaleDetailRes.PriceInfo> priceList = sale.getSalePriceList().stream()
                    .map(price -> new GetSaleDetailRes.PriceInfo(
                            price.getPeriod(),
                            price.getPrice()
                    ))
                    .toList();

            return new GetSaleListRes(
                    sale.getName(),
                    sale.getDescription(),
                    productList,
                    priceList
            );
        });
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        Sale sale = saleRepository.findByIdxAndCategoryIdx(saleIdx, categoryIdx)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 판매상품이 없음"));

        List<GetSaleDetailRes.ProductInfo> productList = sale.getSaleHasProductList().stream()
                .map(shp -> new GetSaleDetailRes.ProductInfo(
                        shp.getProduct().getCode(),
                        shp.getCondition().getName()
                ))
                .toList();

        List<GetSaleDetailRes.PriceInfo> priceList = sale.getSalePriceList().stream()
                .map(price -> new GetSaleDetailRes.PriceInfo(
                        price.getPeriod(),
                        price.getPrice()
                ))
                .toList();

        return new GetSaleDetailRes(
                sale.getName(),
                sale.getDescription(),
                categoryIdx,
                productList,
                priceList
        );
    }

    public List<GetCategoryRes> getSaleCategoryList() {
        return categoryRepository.findAll().stream()
                .map(c -> new GetCategoryRes(
                        c.getIdx(),
                        c.getName(),
                        c.getIconUrl(),
                        c.getParent() != null ? c.getParent().getIdx() : null
                ))
                .toList();
    }
}





