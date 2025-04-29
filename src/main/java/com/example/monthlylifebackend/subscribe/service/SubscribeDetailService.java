package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.subscribe.repository.SubscribeDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class SubscribeDetailService {
    private final SubscribeDetailRepository subscribeDetailRepository;

    public Long SumOfMothlyPrice() {

        // 이번 달 1일 00:00:00
        LocalDateTime startOfMonth = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();

        // 이번 달 1일 + 1달
        LocalDateTime startOfMonthPlus1 = startOfMonth.plusMonths(1);

        Long totalPrice = subscribeDetailRepository.sumOfMonthlyPrice(startOfMonthPlus1);

        if (totalPrice == null) {
            totalPrice = 0L;
        }
        return totalPrice;

    }
}
