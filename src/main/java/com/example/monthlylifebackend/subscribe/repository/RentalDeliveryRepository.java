package com.example.monthlylifebackend.subscribe.repository;

import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalDeliveryRepository extends JpaRepository<RentalDelivery,Long> {


    @Query(value = """
    SELECT rd.*
    FROM rental_delivery rd
    JOIN subscribe_detail sd ON rd.subscribe_detail_idx = sd.idx
    JOIN subscribe s ON sd.subscribe_idx = s.idx
    JOIN user u ON s.user_id = u.id
    WHERE u.id = :userId
      AND sd.idx = :detailIdx
    ORDER BY rd.created_at DESC
    LIMIT 1
""", nativeQuery = true)
    RentalDelivery findLatestRentalDeliveryByUserIdAndDetailIdx(@Param("userId") String userId, @Param("detailIdx") Long detailIdx);
}
