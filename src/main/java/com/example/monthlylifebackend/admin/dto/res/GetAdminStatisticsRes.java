package com.example.monthlylifebackend.admin.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class GetAdminStatisticsRes {
    private List<Integer> monthlySales;       // 월별 매출
    private List<Integer> monthlyNewUsers;     // 월별 신규가입
}

