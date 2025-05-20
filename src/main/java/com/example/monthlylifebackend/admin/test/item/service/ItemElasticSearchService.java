package com.example.monthlylifebackend.admin.test.item.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.monthlylifebackend.admin.dto.res.GetProductRes;
import com.example.monthlylifebackend.admin.test.item.elastic.FieldSet;
import com.example.monthlylifebackend.admin.test.item.elastic.ItemDocument;
import com.example.monthlylifebackend.admin.test.item.elastic.QueryFactory;
import com.example.monthlylifebackend.product.dto.res.ProductImageRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemElasticSearchService {

    private final ElasticsearchClient elasticsearchClient;

    public Page<GetProductRes> searchItems(String productName, String manufacturer,
                                           LocalDate startDate, LocalDate endDate,
                                           int page, int size) throws IOException {

        int from = page * size;

        // 🔹 1. 필드셋 정의
        FieldSet itemFieldSet = FieldSet.builder()
                .rawField("product_name.raw")
                .choseongField("product_name.choseong")
                .kor2engField("product_name.kor2eng")
                .eng2korField("product_name.eng2kor")
                .ngramField("product_name.ngram")
                .nestedPath("product_images")
                .nestedField("product_images.productImgUrl")
                .build();

        // 🔹 2. 전체 Query 조립
        Query query = QueryFactory.buildFullSearchQuery(productName, itemFieldSet, manufacturer, startDate, endDate);

        // 🔹 3. SearchRequest 빌드
        SearchRequest request = new SearchRequest.Builder()
                .index("item")
                .from(from)
                .size(size)
                .sort(List.of(
                        new SortOptions.Builder()
                                .field(f -> f.field("_score").order(SortOrder.Desc))
                                .build()
                ))
                .query(query)
                .build();

        // 🔹 4. 검색 실행 및 매핑
        SearchResponse<ItemDocument> response = elasticsearchClient.search(request, ItemDocument.class);

        List<GetProductRes> content = response.hits().hits().stream()
                .map(hit -> {
                    ItemDocument doc = hit.source();
                    return GetProductRes.builder()
                            .productCode(doc.getProductCode())
                            .productName(doc.getProductName())
                            .manufacturer(doc.getManufacturer())
                            .totalStockCount(doc.getTotalStockCount())
                            .availableStockCount(doc.getAvailableStockCount())
                            .createdAt(doc.getCreatedAt())
                            .productImages(doc.getProductImages() != null
                                    ? doc.getProductImages().stream()
                                    .map(img -> new ProductImageRes(img.getProductImgUrl()))
                                    .toList()
                                    : List.of())
                            .build();
                })
                .toList();

        long total = response.hits().total() != null ? response.hits().total().value() : content.size();
        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

}
