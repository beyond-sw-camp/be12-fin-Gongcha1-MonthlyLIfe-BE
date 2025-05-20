package com.example.monthlylifebackend.admin.test.item.elastic;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.json.JsonData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class QueryFactory {

    public static Query buildFullSearchQuery(String input,
                                             FieldSet fieldSet,
                                             String exactManufacturer,
                                             LocalDate startDate,
                                             LocalDate endDate) {

        List<Query> mustQueries = new ArrayList<>();
        List<Query> shouldQueries = new ArrayList<>();
        List<Query> filterQueries = new ArrayList<>();

        // 제조사 검색
        if (exactManufacturer != null && !exactManufacturer.isEmpty()) {
            mustQueries.add(Query.of(q -> q.match(m -> m.field("manufacturer.kor2eng").query(exactManufacturer))));
        }

        // 이름/제품명 검색
        if (input != null && !input.isEmpty()) {
            shouldQueries.addAll(SearchQueryBuilder.buildTextSearchQueries(input, fieldSet));
        }

        // 날짜 필터
        if (startDate != null || endDate != null) {
            String gte = startDate != null ? startDate.atStartOfDay(ZoneId.of("Asia/Seoul")).toInstant().toString() : null;
            String lte = endDate != null ? endDate.atTime(23, 59, 59).atZone(ZoneId.of("Asia/Seoul")).toInstant().toString() : null;

            RangeQuery rangeQuery = RangeQuery.of(r -> r.untyped(u -> u
                    .field("created_at")
                    .gte(gte != null ? JsonData.of(gte) : null)
                    .lte(lte != null ? JsonData.of(lte) : null)
            ));

            filterQueries.add(Query.of(q -> q.range(rangeQuery)));
        }

        // 최종 bool query 조립
        return Query.of(q -> q.bool(b -> {
            if (!mustQueries.isEmpty()) b.must(mustQueries);
            if (!shouldQueries.isEmpty()) {
                b.should(shouldQueries);
                b.minimumShouldMatch("1");
            }
            if (!filterQueries.isEmpty()) b.filter(filterQueries);
            return b;
        }));
    }
}
