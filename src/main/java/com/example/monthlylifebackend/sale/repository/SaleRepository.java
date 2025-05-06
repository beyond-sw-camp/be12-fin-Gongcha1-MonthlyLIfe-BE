package com.example.monthlylifebackend.sale.repository;


import com.example.monthlylifebackend.sale.dto.res.GetSaleListRes;
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
                MIN(sp.price) AS price
                FROM sale s
                JOIN sale_price sp ON s.idx = sp.sale_idx
                JOIN sale_has_product shp ON s.idx = shp.sale_idx
                JOIN product p ON shp.product_code = p.code
                WHERE s.category_idx = :categoryIdx
                GROUP BY s.idx, s.name, s.description, s.category_idx
                ORDER BY s.created_at DESC;
                """,
            nativeQuery = true)
    Slice<GetSaleListRes> findByCategoryIdx(
            @Param("categoryIdx") Long categoryIdx,
            Pageable pageable);

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
                cd.name AS conditionName,
                MIN(sp.price) AS price
                FROM sale s
                JOIN sale_price sp ON s.idx = sp.sale_idx
                JOIN sale_has_product shp ON s.idx = shp.sale_idx
                JOIN product p ON shp.product_code = p.code
                JOIN `condition` cd ON s.condition_idx = cd.idx
                WHERE s.category_idx = :categoryIdx
                AND cd.name LIKE :grade
                AND s.name LIKE :keyword
                GROUP BY s.idx, s.name, s.description, s.category_idx
                ORDER BY s.created_at DESC;
                """,
            nativeQuery = true)
    Slice<GetSaleListRes> findByCategoryIdxAndGradeAndKeyword(
            @Param("categoryIdx") Long categoryIdx, String grade, String keyword,
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
    Page<Sale> findPackageSales(Pageable pageable);

}
