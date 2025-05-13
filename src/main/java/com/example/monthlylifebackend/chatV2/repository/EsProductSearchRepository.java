package com.example.monthlylifebackend.chatV2.repository;

import com.example.monthlylifebackend.chatV2.api.model.document.UserContextDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.scheduling.annotation.Async;

public interface EsProductSearchRepository extends ElasticsearchRepository<UserContextDocument, String> {

}
