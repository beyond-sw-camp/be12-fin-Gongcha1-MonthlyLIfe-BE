package com.example.monthlylifebackend.payment.repository;

import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.model.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByPaymentId(String paymentId);

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
    }


