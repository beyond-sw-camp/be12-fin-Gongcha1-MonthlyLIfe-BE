package com.example.monthlylifebackend.payment.dto.res;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAdminPaymentRes {
    private Long paymentId;
    private String userName;
    private Long amount;
    private String status;   // '결제완료' / '미결제'
    private String overdue;  // 'Y' / 'N'
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime paymentDate;
}
