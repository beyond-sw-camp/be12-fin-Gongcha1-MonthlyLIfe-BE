package com.example.monthlylifebackend.user.repository;


import com.example.monthlylifebackend.user.dto.res.GetAdminRecentUserRes;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("""
SELECT new com.example.monthlylifebackend.user.dto.res.GetAdminUserRes(
    u.id,
    u.email,
    u.name,
    u.address1,
    CASE WHEN EXISTS (
        SELECT sd FROM SubscribeDetail sd
        WHERE sd.subscribe.user.id = u.id
          AND CURRENT_DATE BETWEEN sd.startAt AND sd.endAt
    ) THEN 'Y' ELSE 'N' END,
    CASE WHEN EXISTS (
        SELECT s FROM Subscribe s
        WHERE s.user.id = u.id
          AND s.isDelayed = true
    ) THEN 'Y' ELSE 'N' END,
    (SELECT COUNT(sd) FROM SubscribeDetail sd WHERE sd.subscribe.user.id = u.id),
    u.createdAt
)
FROM User u
WHERE
  (:searchType IS NULL OR (
    (:searchType = 'ID' AND (:searchQuery IS NULL OR u.id LIKE %:searchQuery%)) OR
    (:searchType = '주소' AND (:searchQuery IS NULL OR u.address1 LIKE %:searchQuery%))
  ))
  AND (:overdueOnly = false OR EXISTS (
    SELECT s FROM Subscribe s WHERE s.user.id = u.id AND s.isDelayed = true
  ))
  AND (:dateFrom IS NULL OR u.createdAt >= :dateFrom)
  AND (:dateTo IS NULL OR u.createdAt <= :dateTo)
""")
    Page<GetAdminUserRes> findUserListSummary(
            Pageable pageable,
            @Param("searchType") String searchType,
            @Param("searchQuery") String searchQuery,
            @Param("dateFrom") LocalDateTime dateFrom,
            @Param("dateTo") LocalDateTime dateTo,
            @Param("overdueOnly") boolean overdueOnly
    );


    @Query("""
    SELECT FUNCTION('MONTH', u.createdAt), COUNT(u)
    FROM User u
    WHERE FUNCTION('YEAR', u.createdAt) = :year
    GROUP BY FUNCTION('MONTH', u.createdAt)
""")
    List<Object[]> getMonthlyNewUsersRaw(@Param("year") int year);

    @Query("""
    SELECT new com.example.monthlylifebackend.user.dto.res.GetAdminRecentUserRes(
        u.id,
        u.name,
        u.createdAt
    )
    FROM User u
    ORDER BY u.createdAt DESC
""")
    List<GetAdminRecentUserRes> findRecentUsers(Pageable pageable);

}
