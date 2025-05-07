package com.example.monthlylifebackend.payment.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentMethodRes {
    private Long idx;
    private String cardCompany;
    private String cardNumber;
}
