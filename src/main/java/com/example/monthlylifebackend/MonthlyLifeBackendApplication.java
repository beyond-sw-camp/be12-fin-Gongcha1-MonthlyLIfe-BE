package com.example.monthlylifebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
@EnableAsync
public class MonthlyLifeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonthlyLifeBackendApplication.class, args);
    }

}
