package com.example.monthlylifebackend.sale.spec;

import com.example.monthlylifebackend.sale.model.Sale;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class SaleSpec {

    /**
     * 카테고리 필터: category.idx = :categoryIdx
     */
    public static Specification<Sale> byCategory(Long categoryIdx) {
        return (root, query, cb) ->
                cb.equal(root.get("category").get("idx"), categoryIdx);
    }

    /**
     * 키워드 필터: lower(name) LIKE %keyword%
     */
    public static Specification<Sale> byKeyword(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return null;
        }
        return (root, query, cb) ->
                cb.like(
                        cb.lower(root.get("name")),
                        "%" + keyword.toLowerCase() + "%"
                );
    }

    /**
     * 등급 필터: Sale → SaleHasProductList → Product → Condition.code = :grade
     */
    public static Specification<Sale> byGrade(String grade) {
        if (grade == null || grade.isBlank()) {
            return null;
        }
        return (root, query, cb) -> {
            var shpJoin = root.join("saleHasProductList", JoinType.INNER);
            var condJoin = shpJoin.join("condition", JoinType.INNER);
            return cb.equal(condJoin.get("name"), grade);
        };
    }



}
