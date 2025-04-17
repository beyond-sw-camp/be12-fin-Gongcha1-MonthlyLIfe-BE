package com.example.monthlylifebackend.config;

import io.portone.sdk.server.payment.PaymentClient;
import io.portone.sdk.server.payment.billingkey.BillingKeyClient;
import io.portone.sdk.server.payment.paymentschedule.PaymentScheduleClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortOneConfig {

    @Value("${portone.api_secret}")
    private String apiSecret;


    /**
     * API Secret을 사용해 포트원 API 클라이언트를 생성합니다.
     *
     * @param apiSecret 포트원 API Secret입니다.
     * @param apiBase 포트원 REST API 주소입니다. 기본값은 `"https://api.portone.io"`입니다.
     * @param storeId 하위 상점에 대해 기능을 사용할 때 필요한 하위 상점의 ID입니다.
     */
    //즉시 결제
    @Bean
    public PaymentClient paymentClient() {

        return new PaymentClient(apiSecret, "https://api.portone.io", null);
    }

    //예약 결제
    @Bean
    public PaymentScheduleClient paymentScheduleClient() {
        return new PaymentScheduleClient(apiSecret, "http://api.portone.io", null);
    }

    @Bean
    public BillingKeyClient billingKeyClient() {
        return new BillingKeyClient(apiSecret, "http://api.portone.io", null);
    }
}