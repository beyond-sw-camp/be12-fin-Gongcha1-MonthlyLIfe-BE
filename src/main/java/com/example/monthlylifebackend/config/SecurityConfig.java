package com.example.monthlylifebackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 요청 인증 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**" // 전체 허용
                        ).permitAll()
                        .anyRequest().permitAll() // 모든 요청 허용
                )
                // CSRF, 세션, 로그인/로그아웃 등 기능 끄기 (선택)
                .csrf(csrf -> csrf.disable())
                .formLogin(login -> login.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
