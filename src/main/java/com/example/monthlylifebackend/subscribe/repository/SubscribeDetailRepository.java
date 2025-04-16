package com.example.monthlylifebackend.subscribe.repository;

import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubscribeDetailRepository extends JpaRepository<SubscribeDetail, Long> {

    @Query("SELECT d FROM SubscribeDetail d " +
            "JOIN FETCH d.sale s " +
            "JOIN FETCH s.saleHasProductList shp " +
            "JOIN FETCH shp.product p " +
            "WHERE d.idx = :detailIdx AND d.subscribe.user.id = :userId")
    Optional<SubscribeDetail> findWithProductAndUser(@Param("detailIdx") Long detailId, @Param("userId") String userId);

}
