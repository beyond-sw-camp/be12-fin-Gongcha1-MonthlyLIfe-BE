package com.example.monthlylifebackend.elastic;

import com.example.monthlylifebackend.elastic.model.SaleAllDocument;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SaleSearchRepository extends ElasticsearchRepository<SaleAllDocument, Long> {

    @Query("""
    {
      "bool": {
        "should": [
          { "match": { "name": { "query": "?0" } } }
        ]
      }
    }
    """)
    Page<SaleAllDocument> searchByKeyword(String keyword, Pageable pageable);


}
