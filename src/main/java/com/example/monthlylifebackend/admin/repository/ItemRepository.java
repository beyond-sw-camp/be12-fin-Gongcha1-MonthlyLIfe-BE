package com.example.monthlylifebackend.admin.repository;

import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.item.dto.ItemDetailDto;
import com.example.monthlylifebackend.item.model.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ItemRepository extends JpaRepository<Item, Long> {
    // ✅ 전체 상품 재고 요약 조회 (상품 코드 기준)
    @Query("""
        SELECT new com.example.monthlylifebackend.admin.dto.response.GetProductRes(
            p.code,
            p.name,
            p.manufacturer,
            SUM(i.count),
            SUM(CASE WHEN il.name = '창고' THEN i.count ELSE 0 END),
            MIN(i.createdAt)
        )
        FROM Item i
        JOIN i.product p
        JOIN i.itemLocation il
        GROUP BY p.code, p.name, p.manufacturer
    """)
    List<GetProductRes> findProductStockSummary();


    // ✅ 전체 상품 재고 요약 조회 + 페이징 (상품 코드 기준)
    @Query("""
    SELECT new com.example.monthlylifebackend.admin.dto.response.GetProductRes(
        p.code,
        p.name,
        p.manufacturer,
        SUM(i.count),
        SUM(CASE WHEN il.name = '창고' THEN i.count ELSE 0 END),
        MIN(i.createdAt)
    )
    FROM Item i
    JOIN i.product p
    JOIN i.itemLocation il
    WHERE (:productName IS NULL OR p.name LIKE :productName)
      AND (:manufacturer IS NULL OR p.manufacturer LIKE :manufacturer)
      AND (:startDate IS NULL OR i.createdAt >= :startDate)
      AND (:endDate IS NULL OR i.createdAt <= :endDate)
    GROUP BY p.code, p.name, p.manufacturer
""")
    Page<GetProductRes> findProductStockSummaryWithConditions(
            Pageable pageable,
            @Param("productName") String productName,
            @Param("manufacturer") String manufacturer,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );


    // ✅ 상품 상세 하위 재고 리스트
    @Query("""
        SELECT new com.example.monthlylifebackend.item.dto.ItemDetailDto(
            i.idx,
            c.name,
            il.name,
            i.count,
            i.createdAt
        )
        FROM Item i
        JOIN i.condition c
        JOIN i.itemLocation il
        WHERE i.product.code = :productCode
    """)
    List<ItemDetailDto> findStockDetailsByProductCode(@Param("productCode") String productCode);
}
