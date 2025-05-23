package com.example.monthlylifebackend.payment.repository;

import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.dto.res.GetAdminRecentPaymentRes;
import com.example.monthlylifebackend.payment.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);

    @Query("""
        SELECT DISTINCT p FROM Payment p
        LEFT JOIN FETCH p.subscribe s
        LEFT JOIN FETCH s.user u
        LEFT JOIN FETCH s.subscribeDetailList d
        LEFT JOIN FETCH d.sale sa
        LEFT JOIN FETCH sa.category c
        WHERE p.scheduledAt >= :start AND p.scheduledAt < :end
    """)
    List<Payment> findAllByScheduledAtGreaterThanEqualAndScheduledAtLessThan(LocalDateTime start, LocalDateTime end);


    //Todo: 이부분도 QueryDSL 이용하면 좋을 것 같은 부분
    @Query("""
SELECT new com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes(
    p.idx,
    u.name,
    p.price,
    CASE WHEN p.isPaid = true THEN '결제 완료' ELSE '결제 전' END,
    CASE WHEN s.isDelayed = true THEN 'Y' ELSE 'N' END,
    p.createdAt
)
FROM Payment p
JOIN p.subscribe s
JOIN s.user u
WHERE
  (:searchType IS NULL OR (
      (:searchType = 'Payment ID' AND (:searchQuery IS NULL OR CAST(p.idx AS string) LIKE %:searchQuery%)) OR
      (:searchType = '사용자명' AND (:searchQuery IS NULL OR u.name LIKE %:searchQuery%))
  ))
  AND (:overdueOnly = false OR s.isDelayed = true)
  AND (:dateFrom IS NULL OR p.createdAt >= :dateFrom)
  AND (:dateTo IS NULL OR p.createdAt <= :dateTo)
""")
    Page<GetAdminPaymentRes> findAdminPayments(
            Pageable pageable,
            @Param("searchType") String searchType,
            @Param("searchQuery") String searchQuery,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("overdueOnly") boolean overdueOnly
    );

    @Query("SELECT SUM(p.price) " +
            "FROM Payment p " +
            "WHERE p.isPaid = true " +
            "AND FUNCTION('YEAR', p.createdAt) = :year")
    Long sumTotalPaid(@Param("year") int year);

    @Query("""
    SELECT FUNCTION('MONTH', p.createdAt), SUM(p.price)
    FROM Payment p
    WHERE p.isPaid = true
    AND FUNCTION('YEAR', p.createdAt) = :year
    GROUP BY FUNCTION('MONTH', p.createdAt)
""")
    List<Object[]> getMonthlySalesRaw(@Param("year") int year);

    @Query("""
    SELECT new com.example.monthlylifebackend.payment.dto.res.GetAdminRecentPaymentRes(
        p.idx,
        u.name,
        p.price,
        CASE WHEN p.isPaid = true THEN '결제완료' ELSE '미결제' END
    )
    FROM Payment p
    JOIN p.subscribe s
    JOIN s.user u
    ORDER BY p.createdAt DESC
""")
    List<GetAdminRecentPaymentRes> findRecentPayments(Pageable pageable);

}


