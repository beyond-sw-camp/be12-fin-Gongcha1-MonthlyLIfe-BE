package com.example.monthlylifebackend.sale.service;

import com.example.monthlylifebackend.admin.repository.ItemRepository;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SaleHandler;
import com.example.monthlylifebackend.elastic.SaleSearchRepository;
import com.example.monthlylifebackend.elastic.model.SaleAllDocument;
import com.example.monthlylifebackend.elastic.model.SaleDocument;
import com.example.monthlylifebackend.product.dto.res.GetCategoryRes;
import com.example.monthlylifebackend.product.model.ProductImage;
import com.example.monthlylifebackend.sale.dto.req.PatchSaleReq;
import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import com.example.monthlylifebackend.sale.dto.res.*;
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
    private final SaleSearchRepository saleSearchRepository;

    @Transactional
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

    //    public Page<GetSaleListRes> getSalesByCategory(Long categoryIdx, int page, int size) {
//        return saleRepository.findByCategoryIdx(categoryIdx, PageRequest.of(page, size))
//                .map(saleMapper::toGetSaleListRes);
//    }
    public Slice<GetSaleListSliceRes> getSalesByCategory(Long categoryIdx, int page, int size) {
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

    public Slice<GetSaleListRes> getSaleProductList(int page, int size) {
        // 필요에 따라 정렬도 추가 가능 (예: 최신순)
        Pageable pageable = PageRequest.of(page, size, Sort.by("idx").descending());

        Slice<Sale> slice = saleRepository.findSliceBy(pageable);

        return slice.map(saleMapper::toGetSaleListRes);
    }
//public Slice<GetSaleWeatherRes> getWeatherSales(int page, int size) {
//    Pageable pg = PageRequest.of(page, size, Sort.by("createdAt").descending());
//    Slice<Sale> slice = saleRepository.findSliceBy(pg);
//
//    return slice.map(sale -> {
//        GetSaleWeatherRes dto = saleMapper.toGetSaleWeatherRes(sale);
//        // 썸네일·조건·최저가·기간 추가 로직을 수동으로 채워야 할 수도 있습니다.
//        // 예: dto.setImageUrl( … ); dto.setConditionName( … );
//        return dto;
//    });
//}

    public SalePrice getSalePrice(Long salePriceIdx) {
        return salePriceRepository.findById(salePriceIdx)
                .orElseThrow(() -> new SaleHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));
    }

//    public Page<GetSaleListRes> getSaleSearch(
//            Long categoryIdx,
//            int page,
//            int size,
//            String keyword,
//            String grade
//    ) {
//        PageRequest pageable = PageRequest.of(page, size);
//        Specification<Sale> spec = Specification
//                .where(SaleSpec.byCategory(categoryIdx))
//                .and(SaleSpec.byKeyword(keyword))
//                .and(SaleSpec.byGrade(grade));
//
//        return saleRepository.findAll(spec, pageable)
//                .map(saleMapper::toGetSaleListRes);
//    }
    
    // 엘라스틱 전
//    public Slice<GetSaleListSliceRes> getSaleSearch(
//            Long categoryIdx,
//            int page,
//            int size,
//            String keyword,
//            String grade
//    ) {
//        if (keyword == null) keyword = "";
//        if (grade == null) grade = "";
//        PageRequest pageable = PageRequest.of(page, size);
//        grade = grade.concat("%");
//        keyword = "%".concat(keyword.concat("%"));
//
//        return saleRepository.findByCategoryIdxAndGradeAndKeyword(categoryIdx, grade, keyword, pageable);
//    }
    // 엘라스틱 적용 후
    public Slice<GetSaleListSliceRes> getSaleSearch(Long categoryIdx, int page, int size, String keyword, String grade) {
        PageRequest pageable = PageRequest.of(page, size);
        if (grade == null) grade = "";
        grade = grade + "%";

        List<Long> saleIds = null;

        // Elasticsearch 검색
        if (keyword != null && !keyword.isBlank()) {
            Page<SaleAllDocument> esResult = saleSearchRepository.searchByKeyword(keyword, pageable);
            saleIds = esResult.stream().map(SaleAllDocument::getIdx).toList();

            if (saleIds.isEmpty()) {
                return new SliceImpl<>(List.of(), pageable, false); // 결과 없음
            }
        }

        // 조건 분기
        if (saleIds == null) {
            return saleRepository.findFilteredWithoutKeyword(categoryIdx, grade, pageable);
        } else {
            return saleRepository.findFilteredByElasticSearch(categoryIdx, grade, saleIds, pageable);
        }
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


    public Slice<PackageSaleRes> getPackageSales(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("idx").descending());

        Slice<Sale> pkgSlice = saleRepository.findPackageSalesSlice(pageable);

        return pkgSlice.map(sale -> {
            List<PackageSaleRes.ProductInfo> prods = sale.getSaleHasProductList().stream()
                    .map(sp -> {
                        List<String> urls = sp.getProduct()
                                .getProductImageList()
                                .stream()
                                .map(pi -> pi.getProductImgUrl())
                                .collect(Collectors.toList());

                        return new PackageSaleRes.ProductInfo(
                                sp.getProduct().getCode(),
                                urls,
                                sp.getCondition().getName()
                        );
                    })
                    .toList();

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

    // 엘라스틱 전
    public Slice<GetSaleListRes> searchByKeyword(String keyword, int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Specification<Sale> spec = Specification
                .where(SaleSpec.byKeyword(keyword));         // 오직 키워드 조건만

        return saleRepository.findAll(spec, pageable)
                .map(saleMapper::toGetSaleListRes);
    }

    // 엘라스틱 적용 후
//    public Slice<GetSaleListRes> searchByKeyword(String keyword, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//
//        // 1. ES로 상품명 검색해서 idx만 추출
//        Page<SaleAllDocument> result = saleSearchRepository.searchByKeyword(keyword, pageable);
//        List<Long> idxList = result.getContent().stream()
//                .map(SaleAllDocument::getIdx)
//                .toList();
//        System.out.println("🔍 Elasticsearch에서 검색된 saleIdx 리스트: " + idxList);
//        if (idxList.isEmpty()) {
//            return new SliceImpl<>(List.of(), pageable, false);
//        }
//
//        // 2. DB에서 실제 상품 정보 조회
//        List<Sale> sales = saleRepository.findByIdxIn(idxList);
//        System.out.println("🧩 DB에서 실제 조회된 Sale 개수: " + sales.size());
//
//
//        // 3. Entity → DTO 변환
//        List<GetSaleListRes> dtoList = sales.stream()
//                .map(sale -> new GetSaleListRes(
//                        sale.getIdx(),
//                        sale.getName(),
//                        sale.getDescription(),
//                        sale.getCategory().getIdx(),
//                        sale.getSaleHasProductList().stream()
//                                .map(item -> new GetSaleListRes.ProductInfo(
//                                        item.getProduct().getCode(),
//                                        item.getCondition().getName(),
//                                        item.getProduct().getProductImageList().stream()
//                                                .map(ProductImage::getProductImgUrl)
//                                                .toList()
//                                ))
//                                .toList(),
//                        sale.getSalePriceList().stream()
//                                .map(price -> new GetSaleListRes.PriceInfo(price.getPeriod(), price.getPrice()))
//                                .toList()
//                ))
//                .toList();
//
//        return new SliceImpl<>(dtoList, pageable, result.hasNext());
//    }




    public List<GetBestSaleRes> getCategoryBestSales(int limit, Long categoryIdx) {
        Pageable pg = PageRequest.of(0, limit);
        return saleRepository.findCategoryBestSummaries(categoryIdx, pg);
    }

//    public List<BestSaleListRes> getCategoryBestSales(int limit, Long categoryIdx) {
//        var pageReq = PageRequest.of(0, limit);
//        List<Object[]> rows = saleRepository.findCategoryBestSalesWithCount(pageReq, categoryIdx);
//
//        return rows.stream().map(row -> {
//            Sale sale = (Sale) row[0];
//            Long count = (Long) row[1];
//
//            // 상품 정보 매핑
//            var prods = sale.getSaleHasProductList().stream()
//                    .map(sp -> new BestSaleListRes.SaleProductInfo(
//                            sp.getProduct().getCode(),
//                            sp.getCondition().getIdx()
//                    ))
//                    .toList();
//
//            // 가격 정보 매핑
//            var prices = sale.getSalePriceList().stream()
//                    .map(p -> new BestSaleListRes.SalePriceInfo(
//                            p.getPeriod(),
//                            p.getPrice()
//                    ))
//                    .toList();
//
//            return new BestSaleListRes(
//                    sale.getIdx(),
//                    sale.getName(),
//                    sale.getDescription(),
//                    sale.getCategory().getIdx(),
//                    prods,
//                    prices,
//                    count
//            );
//        }).toList();
//    }

    /**
     * 최신 상품 N개 조회
     */
    public List<NewSaleListRes> getNewArrivals(int limit) {
        return saleRepository.findTopNewArrivals(limit);
    }

    public List<GetBestSaleRes> getAllBestSales(int limit) {
        Pageable page = PageRequest.of(0, limit);
        return saleRepository.findAllBestSales(page);
    }

}
