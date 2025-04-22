package com.example.monthlylifebackend.subscribe.repository;

import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscribeDetailRepository extends JpaRepository<SubscribeDetail, Long> {

    @Query("SELECT d FROM SubscribeDetail d " +
            "JOIN FETCH d.sale s " +
            "JOIN FETCH s.saleHasProductList shp " +
            "JOIN FETCH shp.product p " +
            "WHERE d.idx = :detailIdx AND d.subscribe.user.id = :userId")
    Optional<SubscribeDetail> findWithProductAndUser(@Param("detailIdx") Long detailId, @Param("userId") String userId);

    @Modifying
    @Query("UPDATE SubscribeDetail sd SET sd.status = :status WHERE sd.idx = :id")
    void updateStatus(@Param("id") Long id, @Param("status") SubscribeStatus status);
    @Query("""
    SELECT d FROM SubscribeDetail d
    WHERE d.subscribe.idx= :subscribeId
      AND d.status = 'SUBSCRIBING'
      AND d.startAt <= CURRENT_TIMESTAMP
      AND d.endAt > CURRENT_TIMESTAMP
    ORDER BY d.endAt DESC
""")
    Optional<SubscribeDetail> findActiveSubscribeDetail(@Param("subscribeId") Long subscribeId);

}
