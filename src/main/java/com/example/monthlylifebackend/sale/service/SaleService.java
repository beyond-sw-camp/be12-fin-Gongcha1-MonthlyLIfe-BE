package com.example.monthlylifebackend.sale.service;

import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SaleHandler;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
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
import jakarta.transaction.Transactional;
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
        // 1) Category 조회
        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_CATEGORY));

        // 2) Sale 생성
        Sale sale = saleMapper.toEntity(dto, category);
        saleRepository.save(sale);

        // 3) Product 연결
        Map<String, Product> productMap = productRepository.findAllByCodeIn(
                dto.getSaleProducts().stream()
                        .map(PostSaleRegisterReq.SaleProductInfo::getProductCode)
                        .distinct()
                        .toList()
        ).stream().collect(Collectors.toMap(Product::getCode, p -> p));

        List<SaleHasProduct> links = dto.getSaleProducts().stream()
                .map(sp -> {
                    Product product = productMap.get(sp.getProductCode());
                    if (product == null) {
                        throw new SaleHandler(ErrorStatus._NOT_FOUND_SALE_PRODUCT);
                    }
                    Condition condition = conditionRepository.findById(sp.getConditionIdx())
                            .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_CONDITION));
                    return new SaleHasProduct(null, sale, product, condition);
                })
                .toList();
        saleHasProductRepository.saveAll(links);

        // 4) SalePrice 생성
        List<SalePrice> prices = saleMapper.toSalePriceList(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(prices);

        return sale.getIdx();
    }

    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        return saleRepository.findByCategoryIdx(categoryIdx, PageRequest.of(page, size))
                .map(saleMapper::toGetSaleListRes);
    }

    public GetSaleDetailRes getSaleDetailInCategory(Long categoryIdx, Long saleIdx) {
        Sale sale = saleRepository.findByIdxAndCategoryIdx(saleIdx, categoryIdx)
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_IN_CATEGORY));
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
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));
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
    @Transactional
    public void deleteSale(Long saleIdx) {
        if (!saleRepository.existsById(saleIdx)) {
            throw new SaleHandler(ErrorStatus._NOT_FOUND_SALE_FOR_DELETE);
        }
        saleRepository.deleteById(saleIdx);
    }

    @Transactional
    public Long updateSale(Long saleIdx, PatchSaleReq dto) {
        // 1) Sale 조회
        Sale sale = saleRepository.findById(saleIdx)
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE));

        // 2) Category 조회
        Category category = categoryRepository.findById(dto.getCategoryIdx())
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_CATEGORY));

        // 3) 필드 수정
        sale.changeName(dto.getName());
        sale.changeDescription(dto.getDescription());
        sale.changeCategory(category);

        // 4) 가격 정보 교체
        salePriceRepository.deleteAllBySaleIdx(saleIdx);
        List<SalePrice> newPrices = saleMapper.toSalePriceListForPatch(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(newPrices);

        return saleIdx;
    }


}
