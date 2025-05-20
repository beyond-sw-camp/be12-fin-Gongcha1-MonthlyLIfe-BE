package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeListProjection;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Query("""
    SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes(
        rd.idx,
        u.name,
        (
            SELECT SUM(sd2.price)
            FROM SubscribeDetail sd2
            WHERE sd2.subscribe.idx = s.idx
        ),
        rd.status,
        u.phoneNumber,
        sd.createdAt
    )
    FROM Subscribe s
    JOIN s.user u
    JOIN s.subscribeDetailList sd
    JOIN sd.rentalDeliveryList rd
    WHERE
      (:searchType IS NULL OR (
          (:searchType = '주문번호' AND (:searchQuery IS NULL OR CAST(s.idx AS string) LIKE %:searchQuery%)) OR
          (:searchType = '주문자명' AND (:searchQuery IS NULL OR u.name LIKE %:searchQuery%)) OR
          (:searchType = '주문상태' AND (:searchQuery IS NULL OR rd.status = %:searchQuery%))
      ))
      AND (:dateFrom IS NULL OR sd.createdAt >= :dateFrom)
      AND (:dateTo IS NULL OR sd.createdAt <= :dateTo)
""")
    Page<GetDeliveryListRes> findDeliveryListByPage(
            Pageable pageable,
            @Param("searchType") String searchType,
            @Param("searchQuery") String searchQuery,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo
    );

    @EntityGraph(attributePaths = {
            "subscribeDetailList",
            "user",
            "billingKey"
    })
    Optional<Subscribe> findByIdx(Long idx);


    @Query("""
                SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes(
                    s.idx,
                    u.name,
                            (
                        SELECT SUM(sd2.price)
                        FROM SubscribeDetail sd2
                        WHERE sd2.subscribe.idx = s.idx
                    ),
                    rd.status,
                    u.phoneNumber,
                    sd.createdAt

                )
                FROM Subscribe s
                JOIN s.user u
                JOIN s.subscribeDetailList sd
                JOIN sd.rentalDeliveryList rd
            """)
    List<GetDeliveryListRes> findDeliveryList(
            String productName, String manufacturer,
            LocalDateTime startDate, LocalDateTime endDate,
            Pageable pageable
    );




    @Query("""
      SELECT sub.idx
      FROM Subscribe sub
      WHERE sub.user.id = :userId
    """)
    Page<Long> findSubscriptionIdsByUser(
            @Param("userId") String userId,
            Pageable pageable
    );

    @Query(
            value = """
    SELECT
      sd.idx            AS subscribeDetailIdx,
      s.idx             AS saleIdx,
      s.name            AS saleName,
      sd.period         AS period,
      sd.price          AS price,
      p.code            AS productCode,
      sub.idx           AS subscribeIdx,
      sub.created_at    AS created_at,
      sd.status         AS status,
      sd.start_at,
      sd.end_at,
      (
        SELECT pi.product_img_url
        FROM product_image pi
        WHERE pi.product_code = p.code
        LIMIT 1
      ) AS productImgUrl,
      (
        SELECT rd.status
        FROM rental_delivery rd
        WHERE rd.subscribe_detail_idx = sd.idx
        ORDER BY rd.created_at DESC
        LIMIT 1
      ) AS deliveryStatus   -- 🔥 추가
    FROM subscribe_detail sd
    JOIN subscribe sub ON sd.subscribe_idx = sub.idx
    JOIN sale s ON sd.sale_idx = s.idx
    JOIN sale_has_product shp ON s.idx = shp.sale_idx
    JOIN product p ON shp.product_code = p.code
    WHERE sub.user_id = :userId
      AND sub.idx IN :subscribeIds
    GROUP BY sd.idx
    ORDER BY sub.idx DESC, sd.idx ASC
""",
            nativeQuery = true
    )
    List<GetSubscribeListProjection> findDetailsBySubscribeIds(
            @Param("userId") String userId,
            @Param("subscribeIds") List<Long> subscribeIds
    );

    //Todo: 추후 QueryDsl을 쓴다면 적용하기 좋은 부분
    @Query("""
SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes(
    s.idx,
    u.name,
    SUM(sd.price),
    MAX(sd.status),
    CASE WHEN s.isDelayed = true THEN 'Y' ELSE 'N' END,
    u.createdAt
)
FROM Subscribe s
JOIN s.user u
LEFT JOIN SubscribeDetail sd ON sd.subscribe.idx = s.idx
WHERE (:keyword IS NULL OR u.name LIKE %:keyword% OR CAST(s.idx AS string) LIKE %:keyword%)
  AND (:status IS NULL OR sd.status = :status)
GROUP BY s.idx, u.name, s.isDelayed, u.createdAt
HAVING (:minMonths IS NULL OR COUNT(sd) >= :minMonths)
   AND (:maxMonths IS NULL OR COUNT(sd) <= :maxMonths)
""")
    Page<GetAdminSubscribeRes> findAdminSubscribe(@Param("pageable") Pageable pageable,
                                                  @Param("keyword") String keyword,
                                                  @Param("status") SubscribeStatus status,
                                                  @Param("minMonths") Integer minMonths,
                                                  @Param("maxMonths") Integer maxMonths);


    @Query("""
SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes(
    sd.idx,
    u.name,
    sd.price,
    SUM(CASE WHEN p.isPaid = true THEN 1 ELSE 0 END),
       CASE WHEN s.isDelayed = true THEN 'Y' ELSE 'N' END,
    sd.status,
    sd.period,
    sd.startAt,
    sd.endAt,
    u.createdAt
)
FROM Subscribe s
JOIN s.user u
JOIN SubscribeDetail sd ON sd.subscribe.idx = s.idx
LEFT JOIN Payment p ON p.subscribe.idx = s.idx
WHERE s.idx = :subscribeId
""")
    List<GetAdminSubscribeDetailRes> findAdminSubscribeDetail(@Param("subscribeId") Long subscribeId);

    List<Subscribe> findAllByUser(User user);

}
