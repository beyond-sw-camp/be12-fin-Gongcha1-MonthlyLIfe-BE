package com.example.monthlylifebackend.subscribe.dto.res;

import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminSubscribeRes {
    private Long subscribeId;
    private String userName;
    private Long planPrice;
    private SubscribeStatus status;      // 구독 상태: "활성", "해지" 등
    private String overdue;   // "Y" or "N"
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime joined;
}
