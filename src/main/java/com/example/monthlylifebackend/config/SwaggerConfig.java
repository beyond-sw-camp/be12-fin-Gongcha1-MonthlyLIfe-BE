package com.example.monthlylifebackend.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    @Bean
    protected Info apiInfo() {
        return new Info()
                .title("Monthly life API")
                .description("월정액 인생 API")
                .version("0.0.1");
    }

    //추후 로그인 추가시 추가
//    @Bean
//    OpenApiCustomizer springSecurityLoginEndpointCustomizer(ApplicationContext applicationContext) {
//        FilterChainProxy springSecurityFilterChain =
//                applicationContext.getBean("springSecurityFilterChain", FilterChainProxy.class);
//
//        return (openApi) -> {
//            // 스프링 필터체인에서 필터들을 반복문을 돌린다.
//            for (SecurityFilterChain filterChain : springSecurityFilterChain.getFilterChains()) {
//                Optional<LoginFilter> loginFilter = filterChain.getFilters()
//                        .stream()
//                        .filter(LoginFilter.class::isInstance)
//                        .map(LoginFilter.class::cast)
//                        .findAny();
//
//                if (loginFilter.isPresent()) {
//                    // 로그인 입력값 스키마
//                    Schema<?> loginSchema = new ObjectSchema()
//                            .addProperty("id", new StringSchema().example("example@example.com"))
//                            .addProperty("password", new StringSchema().example("asdfasdfasdf"));
//
//                    // 로그인 API 추가
//                    addApiPath(openApi, "/login", "POST", "로그인", loginSchema);
//                }
//
//                // 로그아웃 API 추가 (입력값 없음)
//                addApiPath(openApi, "/logout", "GET", "로그아웃", null);
//            }
//        };
//    }
//
//
}
