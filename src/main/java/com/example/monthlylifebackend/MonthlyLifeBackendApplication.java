package com.example.monthlylifebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.monthlylifebackend") // JPA는 여기
@EnableElasticsearchRepositories(basePackages = "com.example.monthlylifebackend.elastic") // ES는 여기!
public class MonthlyLifeBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MonthlyLifeBackendApplication.class, args);
    }

}
