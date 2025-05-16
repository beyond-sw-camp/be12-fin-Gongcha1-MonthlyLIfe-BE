package com.example.monthlylifebackend.elastic;


import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserElasticSearchRepository extends ElasticsearchRepository<UserDoc, String> {
}
