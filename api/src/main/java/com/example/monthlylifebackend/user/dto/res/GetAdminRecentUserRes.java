package com.example.monthlylifebackend.user.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@AllArgsConstructor
public class GetAdminRecentUserRes {
    private String id;
    private String name;
    private LocalDateTime dateJoined;
}
