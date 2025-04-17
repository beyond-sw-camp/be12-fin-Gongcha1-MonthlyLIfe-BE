package com.example.monthlylifebackend.subscribe.repository;


import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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

    @Query("SELECT s FROM Subscribe s JOIN FETCH s.subscribeDetailList d JOIN FETCH d.sale WHERE s.user.id = :userId")
    List<Subscribe> findWithDetailsByUserId(@Param("userId") String userId);
}
