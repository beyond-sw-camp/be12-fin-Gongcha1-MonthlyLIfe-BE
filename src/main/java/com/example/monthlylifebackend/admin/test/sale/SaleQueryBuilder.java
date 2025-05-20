package com.example.monthlylifebackend.admin.test.sale;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class SaleQueryBuilder {

    public List<Query> buildTextSearchQueries(String input, SaleFieldSet fields) {
        List<Query> queries = new ArrayList<>();

        // 정확 일치
        queries.add(Query.of(q -> q.term(t -> t.field(fields.rawField).value(input))));

        // 초성 검색
        if (input.matches("^[ㄱ-ㅎ]+$")) {
            queries.add(Query.of(q -> q.matchPhrasePrefix(m -> m.field(fields.choseongField).query(input))));
        } else if (input.matches("^[a-zA-Z]+$")) {
            queries.add(Query.of(q -> q.matchPhrasePrefix(m -> m.field(fields.kor2engField).query(input))));
            queries.add(Query.of(q -> q.matchPhrasePrefix(m -> m.field(fields.eng2korField).query(input))));
        } else {
            queries.add(Query.of(q -> q.match(m -> m.field(fields.ngramField).query(input))));
        }

        return queries;
    }

}