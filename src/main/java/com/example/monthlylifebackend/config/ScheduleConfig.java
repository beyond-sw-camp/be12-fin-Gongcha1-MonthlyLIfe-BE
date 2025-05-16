package com.example.monthlylifebackend.config;


import com.example.monthlylifebackend.payment.PaymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ScheduleConfig {
    private final PaymentFacade paymentFacade;
    @Scheduled(cron = "0 */10 * * * *", zone = "Asia/Seoul")
    public void dailyPayout() {
        log.info(LocalDateTime.now() + "schedule start");
        long start = System.currentTimeMillis();

        paymentFacade.payout(LocalDateTime.now());
        long end = System.currentTimeMillis();
        log.info("schedule end : "+ (end-start) + "ms");
    }

}
