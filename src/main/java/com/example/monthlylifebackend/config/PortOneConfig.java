package com.example.monthlylifebackend.config;

import io.portone.sdk.server.payment.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortOneConfig {

    @Value("${portone.api_secret}")
    private String apiSecret;

    @Bean
    public PaymentClient portOneClient() {

        return new PaymentClient(apiSecret, "https://api.portone.io", null);
    }
}