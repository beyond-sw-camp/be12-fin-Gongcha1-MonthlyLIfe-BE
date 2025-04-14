package com.example.monthlylifebackend.config;

import com.example.monthlylifebackend.config.filter.JwtFilter;
import com.example.monthlylifebackend.config.filter.LoginFilter;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthenticationConfiguration authConfiguration;
    private final Validator validator;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // CSRF, 세션, 로그인/로그아웃 등 기능 끄기 (선택)
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.sessionManagement(AbstractHttpConfigurer::disable);

        http.authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/**" // 전체 허용
                        ).permitAll()
                        .anyRequest().permitAll() // 모든 요청 허용
                );

        http.addFilterAt(new LoginFilter(authConfiguration.getAuthenticationManager(), validator), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
