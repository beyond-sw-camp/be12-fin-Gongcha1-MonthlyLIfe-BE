package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Integer> {
    @Query("""
    SELECT new com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes(
        s.idx,
        u.name,
        s.price,
        rd.status,
        u.phoneNumber,
        sd.createdAt
    )
    FROM Subscribe s
    JOIN s.user u
    JOIN s.subscribeDetailList sd
    JOIN sd.rentalDeliveryList rd
    """)
    Page<GetDeliveryListRes> findDeliveryList(PageRequest pageRequest);

}
