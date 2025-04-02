package com.example.monthlylifebackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {


    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private Info apiInfo() {
        return new Info()
                .title("월정액 인생 API")
                .description("Springdoc을 사용한 Swagger UI 테스트를 통해 월정액 인생의 백엔드 API를 테스트합니다.")
                .version("1.0.0");
    }


}


