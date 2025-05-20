package com.example.monthlylifebackend.admin.test.sale;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.example.monthlylifebackend.sale.dto.res.GetSaleListSliceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleElasticSearchService {

    private final ElasticsearchClient client;

    public Slice<GetSaleListSliceRes> searchSaleByKeyword(Long categoryIdx, int page, int size, String keyword, String grade) throws IOException {

        SaleFieldSet fieldSet = SaleFieldSet.builder()
                .rawField("name.raw")
                .choseongField("name.choseong")
                .kor2engField("name.kor2eng")
                .eng2korField("name.eng2kor")
                .ngramField("name.ngram")
                .build();

        SearchRequest request = new SearchRequest.Builder()
                .index("sale")
                .from(page * size)
                .size(size)
                .sort(List.of(
                        new SortOptions.Builder()
                                .field(f -> f.field("_score").order(SortOrder.Desc))
                                .build()
                ))
                .query(SaleQueryFactory.buildSearchQuery(keyword, categoryIdx, grade, fieldSet))
                .build();

        SearchResponse<SaleDocument> response = client.search(request, SaleDocument.class);

        List<GetSaleListSliceRes> result = response.hits().hits().stream().map(hit -> {
            SaleDocument doc = hit.source();
            return GetSaleListSliceRes.builder()
                    .idx(doc.getIdx())
                    .name(doc.getName())
                    .description(doc.getDescription())
                    .categoryIdx(doc.getCategoryIdx())
                    .imageUrl(doc.getImageUrl())
                    .conditionName(doc.getConditionName())
                    .price(doc.getPrice())
                    .period(doc.getPeriod())
                    .build();
        }).toList();

        return new SliceImpl<>(result, PageRequest.of(page, size), result.size() == size);
    }
}
