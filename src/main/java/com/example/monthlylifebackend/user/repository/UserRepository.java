package com.example.monthlylifebackend.user.repository;


import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


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
""")
    Page<GetAdminUserRes> findUserListSummary(Pageable pageable);



}
