package com.example.monthlylifebackend.admin.dto.res;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetAdminStatisticsCardRes {
    private Long userCount;
    private Long totalRevenue;
    private Integer activeSubscriptions;
    private Integer repairAndReturnRequests;
    private Long productCount;
}
