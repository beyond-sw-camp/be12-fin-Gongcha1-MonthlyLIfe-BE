package com.example.monthlylifebackend.admin.test.item.elastic;


import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;



@UtilityClass
public class SearchQueryBuilder {

    public List<Query> buildTextSearchQueries(String input, FieldSet fields) {
        List<Query> queries = new ArrayList<>();

        // 정확 일치 (raw 필드)
        queries.add(Query.of(q -> q.term(t -> t.field(fields.rawField).value(input))));

        // 초성 검색
        if (input.matches("^[ㄱ-ㅎ]+$")) {
            queries.add(Query.of(q -> q.matchPhrasePrefix(m -> m.field(fields.choseongField).query(input))));
        }
        // 영문 자판 오타
        else if (input.matches("^[a-zA-Z]+$")) {
            queries.add(Query.of(q -> q.matchPhrasePrefix(m -> m.field(fields.kor2engField).query(input))));
        }
        // 일반 한글 입력
        else {
            queries.add(Query.of(q -> q.match(m -> m.field(fields.ngramField).query(input))));
        }

        // nested 검색 (예: 이미지)
        if (fields.nestedPath != null && fields.nestedField != null) {
            queries.add(Query.of(q -> q.nested(n -> n
                    .path(fields.nestedPath)
                    .query(nq -> nq.match(m -> m.field(fields.nestedField).query(input)))
            )));
        }

        return queries;
    }


}
