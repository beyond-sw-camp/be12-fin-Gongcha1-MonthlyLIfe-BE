package com.example.monthlylifebackend.sale.repository;


import com.example.monthlylifebackend.sale.dto.res.*;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long>, JpaSpecificationExecutor<Sale> {
    // Specification을 이용한 동적 검색 지원


    @Query("SELECT s FROM Sale s LEFT JOIN FETCH s.salePriceList WHERE s.idx = :idx")
    Optional<Sale> findWithSalePricesByIdx(@Param("idx") Long idx);

    @Query("""
                SELECT sp FROM SalePrice sp
                WHERE sp.sale.idx = :saleIdx AND sp.period = :period
            """)
    Optional<SalePrice> findBySaleIdxAndPeriod(@Param("saleIdx") Long saleIdx, @Param("period") int period);

//    Page<Sale> findByCategoryIdx(Long categoryIdx, Pageable pageable);

    @Query(value = """
            SELECT
            s.idx AS idx,
            s.name AS name,
            s.description AS description,
            s.category_idx AS categoryIdx,
            (
                  SELECT pi.product_img_url
                  FROM sale_has_product shp2
                  JOIN product p2 ON shp2.product_code = p2.code
                  JOIN product_image pi ON pi.product_code = p2.code
                  WHERE shp2.sale_idx =
                   s.idx
                  ORDER BY pi.created_at ASC
                  LIMIT 1
            ) AS imageUrl,
            (
                  SELECT cd.name
                  FROM sale_has_product shp2
                  JOIN `condition` cd ON cd.idx = shp2.condition_idx
                  WHERE shp2.sale_idx =
                   s.idx
                  ORDER BY p.created_at ASC
                  LIMIT 1
            ) AS conditionName,
            MIN(sp.price) AS price,
            (
                        SELECT sp2.period
                        FROM sale_price sp2
                        WHERE sp2.sale_idx = s.idx
                        ORDER BY sp2.price ASC
                        LIMIT 1
            ) AS period
            
            FROM sale s
            JOIN sale_price sp ON s.idx = sp.sale_idx
            JOIN sale_has_product shp ON s.idx = shp.sale_idx
            JOIN product p ON shp.product_code = p.code
            WHERE s.category_idx = :categoryIdx
            GROUP BY s.idx, s.name, s.description, s.category_idx
            ORDER BY s.created_at DESC;
            """,
            nativeQuery = true)
    Slice<GetSaleListSliceRes> findByCategoryIdx(
            @Param("categoryIdx") Long categoryIdx,
            Pageable pageable);


    Optional<Sale> findByIdxAndCategoryIdx(Long saleIdx, Long categoryIdx);

    @Query("""
              SELECT s, SUM(CASE WHEN sd.status = 'SUBSCRIBING' THEN 1 ELSE 0 END)
              FROM Sale s
              LEFT JOIN s.subscribeDetailList sd
              GROUP BY s
              ORDER BY SUM(CASE WHEN sd.status = 'SUBSCRIBING' THEN 1 ELSE 0 END) DESC
            """)
    List<Object[]> findBestSalesWithCount(Pageable pageable);

    /**
     * sale_has_product 에 매핑된 상품 개수가 2개 이상인 Sale(=패키지)만 페이징 조회
     */
    @Query("""
                SELECT s
                FROM Sale s
                WHERE (SELECT COUNT(sp) 
                         FROM SaleHasProduct sp 
                        WHERE sp.sale = s) > 1
            """)
    Slice<Sale> findPackageSalesSlice(Pageable pageable);


    @Query("""
              SELECT s, 
                     SUM(CASE WHEN sd.status = 'SUBSCRIBING' THEN 1 ELSE 0 END) AS subscribeCount
              FROM Sale s
              LEFT JOIN s.subscribeDetailList sd
              WHERE (:categoryIdx IS NULL OR s.category.idx = :categoryIdx)
              GROUP BY s
              ORDER BY subscribeCount DESC
            """)
    List<Object[]> findCategoryBestSalesWithCount(
            Pageable pageable,
            @Param("categoryIdx") Long categoryIdx
    );

    @Query(value = """
            SELECT
              s.idx                       AS sale_idx,
              s.name                      AS name,
              s.category_idx              AS category_idx,
              (
                /* 첫 번째 상품의 썸네일 */
                SELECT pi.product_img_url
                FROM sale_has_product shp2
                JOIN product p2               ON shp2.product_code = p2.code
                JOIN product_image pi         ON pi.product_code = p2.code
                WHERE shp2.sale_idx = s.idx
                ORDER BY pi.created_at ASC
                LIMIT 1
              )                           AS image_url,
              (
                /* 첫 번째 상품의 제조사 */
                SELECT p3.manufacturer
                FROM sale_has_product shp3
                JOIN product p3              ON shp3.product_code = p3.code
                WHERE shp3.sale_idx = s.idx
                ORDER BY p3.created_at ASC
                LIMIT 1
              )                           AS manufacturer,
              (
                /* 첫 번째 상품의 등급명 */
                SELECT cd.name
                FROM sale_has_product shp4
                JOIN `condition` cd          ON shp4.condition_idx = cd.idx
                WHERE shp4.sale_idx = s.idx
                ORDER BY shp4.created_at ASC
                LIMIT 1
              )                           AS condition_name,
              MIN(sp.price)               AS price,
              (
                SELECT sp2.period
                FROM sale_price sp2
                WHERE sp2.sale_idx = s.idx
                ORDER BY sp2.price ASC
                LIMIT 1
              )                           AS period,
              CAST(SUM(CASE WHEN sd.status='SUBSCRIBING' THEN 1 ELSE 0 END) AS SIGNED) AS subscribe_count
            FROM sale s
            LEFT JOIN subscribe_detail sd ON sd.sale_idx = s.idx
            JOIN sale_price sp            ON sp.sale_idx = s.idx
            WHERE s.category_idx = :categoryIdx
            GROUP BY s.idx, s.name, s.category_idx
            ORDER BY subscribe_count DESC
            """,
            nativeQuery = true)
    List<GetBestSaleRes> findCategoryBestSummaries(
            @Param("categoryIdx") Long categoryIdx,
            Pageable pageable
    );


    Slice<Sale> findSliceBy(Pageable pageable);


    @Query(
            value = """
                    SELECT
                      s.idx               AS idx,
                      s.name              AS name,
                      s.category_idx      AS categoryIdx,
                      (
                        SELECT pi.product_img_url
                        FROM sale_has_product shp2
                          JOIN product_image pi ON shp2.product_code = pi.product_code
                        WHERE shp2.sale_idx = s.idx
                        ORDER BY pi.created_at
                        LIMIT 1
                      )                   AS imageUrl,
                      (
                        SELECT cd.name
                        FROM sale_has_product shp3
                          JOIN `condition` cd ON shp3.condition_idx = cd.idx
                        WHERE shp3.sale_idx = s.idx
                        ORDER BY shp3.created_at
                        LIMIT 1
                      )                   AS conditionName,
                      MIN(sp.price)       AS price,
                      (
                        SELECT sp2.period
                        FROM sale_price sp2
                        WHERE sp2.sale_idx = s.idx
                        ORDER BY sp2.price
                        LIMIT 1
                      )                   AS period
                    FROM sale s
                      JOIN sale_price sp ON s.idx = sp.sale_idx
                    GROUP BY
                      s.idx, s.name, s.category_idx
                    ORDER BY
                      s.created_at DESC
                    LIMIT :limit
                    """,
            nativeQuery = true
    )
    List<NewSaleListRes> findTopNewArrivals(@Param("limit") int limit);


    @Query(value = """
            SELECT
              s.idx               AS sale_idx,
              s.name              AS name,
              s.category_idx      AS category_idx,
              (
                SELECT pi.product_img_url
                FROM sale_has_product shp2
                JOIN product_image pi
                  ON shp2.product_code = pi.product_code
                WHERE shp2.sale_idx = s.idx
                ORDER BY pi.created_at ASC
                LIMIT 1
              ) AS image_url,
              (
                SELECT p3.manufacturer
                FROM sale_has_product shp3
                JOIN product p3
                  ON shp3.product_code = p3.code
                WHERE shp3.sale_idx = s.idx
                ORDER BY shp3.created_at ASC
                LIMIT 1
              ) AS manufacturer,
              (
                SELECT cd.name
                FROM sale_has_product shp4
                JOIN `condition` cd
                  ON shp4.condition_idx = cd.idx
                WHERE shp4.sale_idx = s.idx
                ORDER BY shp4.created_at ASC
                LIMIT 1
              ) AS condition_name,
              MIN(sp.price) AS price,
              (
                SELECT sp2.period
                FROM sale_price sp2
                WHERE sp2.sale_idx = s.idx
                ORDER BY sp2.price ASC
                LIMIT 1
              ) AS period,
            
              (
                SELECT COUNT(*)
                FROM subscribe_detail sd2
                WHERE sd2.sale_idx = s.idx
                  AND sd2.status   = 'SUBSCRIBING'
              ) AS subscribe_count
            
            FROM sale s
              JOIN sale_price sp
                ON sp.sale_idx = s.idx
            
            GROUP BY
              s.idx, s.name, s.category_idx
            
            ORDER BY
              subscribe_count DESC
            """,
            nativeQuery = true)
    List<GetBestSaleRes> findAllBestSales(Pageable pageable);


    @Query(value = """
    SELECT
      s.idx               AS idx,
      s.name              AS name,
      s.description       AS description,
      s.category_idx      AS categoryIdx,
      (
        /* 첫 번째 상품 썸네일 */
        SELECT pi.product_img_url
        FROM sale_has_product shp2
          JOIN product p2 ON shp2.product_code = p2.code
          JOIN product_image pi ON pi.product_code = p2.code
        WHERE shp2.sale_idx = s.idx
        ORDER BY pi.created_at ASC
        LIMIT 1
      )                    AS imageUrl,
      cd.name             AS conditionName,
      MIN(sp.price)       AS price,
      (
        /* 최저가 옵션의 기간 */
        SELECT sp2.period
        FROM sale_price sp2
        WHERE sp2.sale_idx = s.idx
        ORDER BY sp2.price ASC
        LIMIT 1
      )                    AS period
    FROM sale s
      JOIN sale_price sp    ON s.idx = sp.sale_idx
      JOIN sale_has_product shp ON s.idx = shp.sale_idx
      JOIN product p        ON shp.product_code = p.code
      JOIN `condition` cd   ON shp.condition_idx = cd.idx
    WHERE s.category_idx = :categoryIdx
      AND cd.name   LIKE :grade
      AND s.name    LIKE :keyword
    GROUP BY s.idx, s.name, s.description, s.category_idx, cd.name
    ORDER BY s.created_at DESC
    """,
            nativeQuery = true
    )
    Slice<GetSaleListSliceRes> findByCategoryIdxAndGradeAndKeyword(
            @Param("categoryIdx") Long categoryIdx,
            @Param("grade") String grade,
            @Param("keyword") String keyword,
            Pageable pageable
    );


    @Query(value = """
        SELECT
          s.idx               AS idx,
          s.name              AS name,
          s.description       AS description,
          s.category_idx      AS categoryIdx,
          (
            SELECT pi.product_img_url
            FROM sale_has_product shp2
            JOIN product p2 ON shp2.product_code = p2.code
            JOIN product_image pi ON pi.product_code = p2.code
            WHERE shp2.sale_idx = s.idx
            ORDER BY pi.created_at ASC
            LIMIT 1
          )                  AS imageUrl,
          cd.name             AS conditionName,
          MIN(sp.price)       AS price,
          (
            SELECT sp2.period
            FROM sale_price sp2
            WHERE sp2.sale_idx = s.idx
            ORDER BY sp2.price ASC
            LIMIT 1
          )                  AS period
        FROM sale s
        JOIN sale_price sp ON s.idx = sp.sale_idx
        JOIN sale_has_product shp ON s.idx = shp.sale_idx
        JOIN product p ON shp.product_code = p.code
        JOIN `condition` cd ON shp.condition_idx = cd.idx
        WHERE s.category_idx = :categoryIdx
          AND cd.name LIKE :grade
        GROUP BY s.idx, s.name, s.description, s.category_idx, cd.name
        ORDER BY s.created_at DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT s.idx)
        FROM sale s
        JOIN sale_price sp ON s.idx = sp.sale_idx
        JOIN sale_has_product shp ON s.idx = shp.sale_idx
        JOIN product p ON shp.product_code = p.code
        JOIN `condition` cd ON shp.condition_idx = cd.idx
        WHERE s.category_idx = :categoryIdx
          AND cd.name LIKE :grade
        """,
            nativeQuery = true
    )
    Slice<GetSaleListSliceRes> findFilteredWithoutKeyword(
            @Param("categoryIdx") Long categoryIdx,
            @Param("grade") String grade,
            Pageable pageable
    );

    @Query(value = """
        SELECT
          s.idx               AS idx,
          s.name              AS name,
          s.description       AS description,
          s.category_idx      AS categoryIdx,
          (
            SELECT pi.product_img_url
            FROM sale_has_product shp2
            JOIN product p2 ON shp2.product_code = p2.code
            JOIN product_image pi ON pi.product_code = p2.code
            WHERE shp2.sale_idx = s.idx
            ORDER BY pi.created_at ASC
            LIMIT 1
          )                  AS imageUrl,
          cd.name             AS conditionName,
          MIN(sp.price)       AS price,
          (
            SELECT sp2.period
            FROM sale_price sp2
            WHERE sp2.sale_idx = s.idx
            ORDER BY sp2.price ASC
            LIMIT 1
          )                  AS period
        FROM sale s
        JOIN sale_price sp ON s.idx = sp.sale_idx
        JOIN sale_has_product shp ON s.idx = shp.sale_idx
        JOIN product p ON shp.product_code = p.code
        JOIN `condition` cd ON shp.condition_idx = cd.idx
        WHERE s.category_idx = :categoryIdx
          AND cd.name LIKE :grade
          AND s.idx IN (:saleIds)
        GROUP BY s.idx, s.name, s.description, s.category_idx, cd.name
        ORDER BY s.created_at DESC
        """,
            countQuery = """
        SELECT COUNT(DISTINCT s.idx)
        FROM sale s
        JOIN sale_price sp ON s.idx = sp.sale_idx
        JOIN sale_has_product shp ON s.idx = shp.sale_idx
        JOIN product p ON shp.product_code = p.code
        JOIN `condition` cd ON shp.condition_idx = cd.idx
        WHERE s.category_idx = :categoryIdx
          AND cd.name LIKE :grade
          AND s.idx IN (:saleIds)
        """,
            nativeQuery = true
    )
    Slice<GetSaleListSliceRes> findFilteredByElasticSearch(
            @Param("categoryIdx") Long categoryIdx,
            @Param("grade") String grade,
            @Param("saleIds") List<Long> saleIds,
            Pageable pageable
    );

    List<Sale> findByIdxIn(List<Long> idxList);

}
