package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeListProjection;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    @Query("""
                SELECT new com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes(
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
    Page<GetDeliveryListRes> findDeliveryListByPage(Pageable pageable);

    @Query("""
                SELECT new com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes(
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
    List<GetDeliveryListRes> findDeliveryList();




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
            WHERE pi.product_idx = p.code
            LIMIT 1
          )                   AS productImgUrl
        FROM subscribe_detail sd
          JOIN subscribe sub   ON sd.subscribe_idx = sub.idx
          JOIN sale s          ON sd.sale_idx      = s.idx
          JOIN sale_has_product shp ON s.idx       = shp.sale_idx
          JOIN product p       ON shp.product_code = p.code
        WHERE sub.user_id = :userId
          AND sub.idx    IN :subscribeIds
        GROUP BY sd.idx
        ORDER BY sub.idx DESC, sd.idx ASC
      """,
            nativeQuery = true
    )
    List<GetSubscribeListProjection> findDetailsBySubscribeIds(
            @Param("userId") String userId,
            @Param("subscribeIds") List<Long> subscribeIds
    );


}
