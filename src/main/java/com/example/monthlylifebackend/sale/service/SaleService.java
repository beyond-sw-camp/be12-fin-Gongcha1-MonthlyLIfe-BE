package com.example.monthlylifebackend.sale.service;

import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SaleHandler;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.product.model.Category;
import com.example.monthlylifebackend.product.model.Condition;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.product.repository.CategoryRepository;
import com.example.monthlylifebackend.product.repository.ConditionRepository;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.BestSaleListRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleDetailRes;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
import com.example.monthlylifebackend.sale.dto.res.PackageSaleRes;
import com.example.monthlylifebackend.sale.mapper.SaleMapper;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.sale.spec.SaleSpec;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
    private final ItemRepository itemRepository;
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
                    // 여기가 핵심: 재고 존재 여부 확인
                    boolean hasStock = itemRepository.existsByProductAndConditionAndCountGreaterThan(
                            product, condition, 0
                    );
                    if (!hasStock) {
                        throw new SaleHandler(ErrorStatus._INSUFFICIENT_STOCK);
                    }
                    return new SaleHasProduct(null, sale, product, condition);
                })
                .toList();
        saleHasProductRepository.saveAll(links);

        // 4) SalePrice 생성
        List<SalePrice> prices = saleMapper.toSalePriceList(dto.getSalePrices(), sale);
        salePriceRepository.saveAll(prices);

        return sale.getIdx();
    }

    public Slice<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
        return saleRepository.findByCategoryIdx(categoryIdx, PageRequest.of(page, size));
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

    public Page<GetSaleListRes> getSaleProductList(int page, int size) {
        // 필요에 따라 정렬도 추가 가능 (예: 최신순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("idx").descending());

        // repository 가 JpaRepository<Sale, Long> 라 가정
        return saleRepository.findAll(pageable)
                .map(saleMapper::toGetSaleListRes);
    }

    public SalePrice getSalePrice(Long salePriceIdx) {
        return salePriceRepository.findById(salePriceIdx)
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));
    }

    public Slice<GetSaleListRes> getSaleSearch(
            Long categoryIdx,
            int page,
            int size,
            String keyword,
            String grade
    ) {
        if (keyword==null) keyword = "";
        if (grade==null) grade = "";
        PageRequest pageable = PageRequest.of(page, size);
        grade = grade.concat("%");
        keyword = "%".concat(keyword.concat("%"));

        return saleRepository.findByCategoryIdxAndGradeAndKeyword(categoryIdx, grade, keyword, pageable);
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

    public List<BestSaleListRes> getBestSales(int limit) {
        var pageReq = PageRequest.of(0, limit);
        List<Object[]> rows = saleRepository.findBestSalesWithCount(pageReq);

        return rows.stream().map(row -> {
            Sale sale = (Sale) row[0];
            Long count = (Long) row[1];

            // SaleProductInfo 매핑
            List<BestSaleListRes.SaleProductInfo> prods = sale.getSaleHasProductList().stream()
                    .map(sp -> new BestSaleListRes.SaleProductInfo(
                            sp.getProduct().getCode(),
                            sp.getCondition().getIdx()
                    )).toList();

            // SalePriceInfo 매핑
            List<BestSaleListRes.SalePriceInfo> prices = sale.getSalePriceList().stream()
                    .map(p -> new BestSaleListRes.SalePriceInfo(
                            p.getPeriod(), p.getPrice()
                    )).toList();

            return new BestSaleListRes(
                    sale.getIdx(),
                    sale.getName(),
                    sale.getDescription(),
                    sale.getCategory().getIdx(),
                    prods,
                    prices,
                    count
            );

        }).toList();
    }




    public Page<PackageSaleRes> getPackageSales(int page, int size) {
        Page<Sale> pkgPage = saleRepository.findPackageSales(
                PageRequest.of(page, size, Sort.by("idx").descending())
        );

        return pkgPage.map(sale -> {
            // 상품 리스트(DTO)로 변환
            List<PackageSaleRes.ProductInfo> prods = sale.getSaleHasProductList().stream()
                    .map(sp -> new PackageSaleRes.ProductInfo(
                            sp.getProduct().getCode(),
                            sp.getProduct().getProductImageList().stream()
                                    .findFirst()
                                    .map(pi -> pi.getProductImgUrl())
                                    .orElse("/assets/images/placeholder.png"),
                            sp.getCondition().getName()
                    ))
                    .toList();

            // 가격 리스트(DTO)로 변환
            List<PackageSaleRes.PriceInfo> prices = sale.getSalePriceList().stream()
                    .map(p -> new PackageSaleRes.PriceInfo(p.getPeriod(), p.getPrice()))
                    .toList();

            return new PackageSaleRes(
                    sale.getIdx(),
                    sale.getName(),
                    sale.getDescription(),
                    sale.getCategory().getIdx(),
                    prods,
                    prices
            );
        });
    }

    public Page<GetSaleListRes> searchByKeyword(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Specification<Sale> spec = Specification
                .where(SaleSpec.byKeyword(keyword));         // 오직 키워드 조건만

        return saleRepository.findAll(spec, pageable)
                .map(saleMapper::toGetSaleListRes);
    }


}
