package com.example.monthlylifebackend.payment.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GetAdminRecentPaymentRes {
    private Long id;
    private String customerName;
    private Long amount;
    private String status;
}
