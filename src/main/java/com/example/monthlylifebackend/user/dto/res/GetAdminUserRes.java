package com.example.monthlylifebackend.user.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminUserRes {
    private String id;
    private String email;
    private String name;
    private String address1;
    private String overdue;          // Y / N
    private List<String> tags;       // 아직 없음 일단은 null로 처리
    private String isDelayed;        // Y / N
    private Long subscriptionCount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime joined;

    public GetAdminUserRes(String id, String email, String name, String address1,
                           String overdue, String isDelayed,
                           Long subscriptionCount, LocalDateTime joined) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address1 = address1;
        this.overdue = overdue;
        this.isDelayed = isDelayed;
        this.subscriptionCount = subscriptionCount;
        this.joined = joined;
        this.tags = null;
    }
}