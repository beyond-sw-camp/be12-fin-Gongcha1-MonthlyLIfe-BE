package com.example.monthlylifebackend.admin.test.sale;

import co.elastic.clients.elasticsearch._types.query_dsl.Query;

import java.util.ArrayList;
import java.util.List;

public class SaleQueryFactory {

    public static Query buildSearchQuery(String keyword, Long categoryIdx, String grade, SaleFieldSet fields) {
        List<Query> filterQueries = new ArrayList<>();

        // 카테고리 필터
        if (categoryIdx != null) {
            filterQueries.add(Query.of(q -> q.term(t -> t.field("category_idx").value(categoryIdx))));
        }

        // 상태(grade) 필터
        if (grade != null && !grade.isBlank()) {
            filterQueries.add(Query.of(q -> q.term(t -> t.field("condition_name").value(grade))));
        }

        // 키워드가 비어 있으면 match_all 쿼리 사용
        if (keyword == null || keyword.isBlank()) {
            return Query.of(q -> q.bool(b -> {
                b.must(m -> m.matchAll(ma -> ma));
                if (!filterQueries.isEmpty()) b.filter(filterQueries);
                return b;
            }));
        }

        // 키워드 검색 조건
        List<Query> shouldQueries = SaleQueryBuilder.buildTextSearchQueries(keyword, fields);

        return Query.of(q -> q.bool(b -> {
            if (!filterQueries.isEmpty()) b.filter(filterQueries);
            b.should(shouldQueries).minimumShouldMatch("1");
            return b;
        }));
    }
}
