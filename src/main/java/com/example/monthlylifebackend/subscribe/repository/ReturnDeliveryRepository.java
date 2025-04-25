package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDelivery;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ReturnDeliveryRepository  extends JpaRepository<ReturnDelivery,Long> {
    @Query("""
              SELECT rd
                FROM ReturnDelivery rd
                WHERE rd.subscribeDetail.idx = :id
                AND rd.status = 'RETURN_REQUESTED'
            """)
    Optional<ReturnDelivery> findLatestByDetailIdx(@Param("id") Long detailIdx);
    @Modifying
    @Query("UPDATE ReturnDelivery rd " +
            "SET rd.status = :status, rd.updatedAt = :updatedAt " +
            "WHERE rd.subscribeDetail.idx = :detailIdx")
    void updateStatusByDetailIdx(
            @Param("detailIdx") Long detailIdx,
            @Param("status") ReturnDeliveryStatus status,
            @Param("updatedAt") LocalDateTime updatedAt
    );

    @Query("""
    SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes(
        r.idx,
        r.subscribeName,
        r.description,
        r.createdAt,
        r.status,
        r.returnLocation
    )
    FROM ReturnDelivery r
    WHERE (:status IS NULL OR str(r.status) LIKE CONCAT(:status, '%'))
      AND (:dateFrom IS NULL OR r.createdAt >= :dateFrom)
      AND (:dateTo IS NULL OR r.createdAt <= :dateTo)
""")
    Page<GetAdminReturnDeliveryRes> findReturnRequestByFilter(Pageable pageable,
                                                           @Param("status") String part,
                                                           @Param("dateFrom") LocalDateTime dateFrom,
                                                           @Param("dateTo") LocalDateTime dateTo);


    @Query("""
    SELECT new com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes(
        r.idx,
        r.subscribeName,
        r.description,
        r.createdAt,
        r.status,
        r.returnLocation
    )
    FROM ReturnDelivery r
    WHERE (:status IS NULL OR str(r.status) LIKE CONCAT(:status, '%'))
      AND (:dateFrom IS NULL OR r.createdAt >= :dateFrom)
      AND (:dateTo IS NULL OR r.createdAt <= :dateTo)
""")
    Page<GetAdminReturnDeliveryRes> findRepairRequestByFilter(Pageable pageable,
                                                         @Param("status") String part,
                                                         @Param("dateFrom") LocalDateTime dateFrom,
                                                         @Param("dateTo") LocalDateTime dateTo);
}
