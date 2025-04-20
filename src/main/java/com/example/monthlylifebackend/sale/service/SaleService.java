package com.example.monthlylifebackend.sale.service;

import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.mapper.SaleMapper;
import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.CategoryRepository;
import com.example.monthlylifebackend.product.repository.ConditionRepository;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.sale.spec.SaleSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
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

        // Sale 생성
        Sale sale = saleMapper.toEntity(dto, category);
        saleRepository.save(sale);

        // 연관 엔티티 SaleHasProduct 생성
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
                        throw new RuntimeException("등록되지 않은 상품 코드: " + sp.getProductCode());
                    }
                    Condition condition = conditionRepository.findById(sp.getConditionIdx())
                            .orElseThrow(() -> new RuntimeException("Condition 없음"));
                    return new SaleHasProduct(null, sale, product, condition);
                })
                .toList();
        saleHasProductRepository.saveAll(productLinks);

        // SalePrice 생성
        List<SalePrice> prices = saleMapper.toSalePriceList(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(prices);

        return sale.getIdx();
    }

    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        PageRequest pr = PageRequest.of(page, size);
        return saleRepository.findByCategoryIdx(categoryIdx, pr)
                .map(saleMapper::toGetSaleListRes);
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        Sale sale = saleRepository.findByIdxAndCategoryIdx(saleIdx, categoryIdx)
                .orElseThrow(() -> new RuntimeException("해당 카테고리에 판매상품이 없음"));
        return saleMapper.toGetSaleDetailRes(sale);
    }

    public List<GetCategoryRes> getSaleCategoryList() {
        return categoryRepository.findAll().stream()
                .map(saleMapper::toCategoryRes)
                .toList();
    }

    public List<GetSaleListRes> getSaleProductList() {
        return saleRepository.findAll().stream()
                .map(saleMapper::toGetSaleListRes)
                .toList();
    }

    public SalePrice getSalePrice(Long salePriceIdx) {
        return salePriceRepository.findById(salePriceIdx)
                .orElseThrow(() -> new RuntimeException("판매 가격 정보가 없습니다: " + salePriceIdx));
    }

    public Page<GetSaleListRes> getSaleSearch(
            Long categoryIdx,
            int page,
            int size,
            String keyword,
            String grade
    ) {
        PageRequest pageable = PageRequest.of(page, size);
        Specification<Sale> spec = Specification
                .where(SaleSpec.byCategory(categoryIdx))
                .and(SaleSpec.byKeyword(keyword))
                .and(SaleSpec.byGrade(grade));

        return saleRepository.findAll(spec, pageable)
                .map(saleMapper::toGetSaleListRes);
    }
}
