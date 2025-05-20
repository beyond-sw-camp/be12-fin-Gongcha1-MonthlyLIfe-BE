//package com.example.monthlylifebackend.admin.test.delivery;
//
//
//import co.elastic.clients.elasticsearch.ElasticsearchClient;
//import co.elastic.clients.elasticsearch.core.SearchResponse;
//import co.elastic.clients.json.JsonData;
//import com.fasterxml.jackson.databind.JsonNode;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class DeliveryElasticSearch {
//
//    private final ElasticsearchClient elasticsearchClient;
//
//    public List<DeliveryDocument> searchByKeyword(String keyword) throws IOException {
//        SearchResponse<JsonData> response = elasticsearchClient.search(s -> s
//                .index("delivery")
//                .query(q -> q
//                        .multiMatch(m -> m
//                                .query(keyword)
//                                .fields("user_name.raw^10", "user_name.kor2eng^8", "user_name.eng2kor^6", "user_name.choseong^4", "user_name.ngram")
//                        )
//                ), JsonData.class);
//
//        return response.hits().hits().stream().map(hit -> {
//            JsonNode json = hit.source().to(JsonNode.class);
//            return new DeliveryDocument(
//                    hit.id(),
//                    json.get("user_name").asText(),
//                    json.get("subscribe_price").asInt(),
//                    json.get("delivery_status").asText(),
//                    json.get("user_phone").asText(),
//                    json.get("subscribe_detail_created_at").asText()
//            );
//        }).collect(Collectors.toList());
//    }
//}
