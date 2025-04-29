package com.example.monthlylifebackend.config;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.config.filter.JwtFilter;
import com.example.monthlylifebackend.config.filter.LoginFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
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
                                "/admin/**", "/sale/*/update", "sale/*/delete"
                        ).hasRole("ADMIN")
                        .requestMatchers(
                                "/user/register", "user/find-account", "/user/checkid"
                        ).permitAll()
                        .requestMatchers(
                                "/user/**", "/subscribe/**"
                        ).hasAnyRole("USER", "ADMIN")
                        .anyRequest().permitAll() // 모든 요청 허용
                );

        //로그아웃 기능 설정
        http.logout(logout -> logout
                .logoutUrl("/auth/logout")
                .invalidateHttpSession(true)
                .deleteCookies("ATOKEN")
                .logoutSuccessHandler((request, response, authentication) -> {
                    // 로그아웃 후 추가 작업 (예: JWT 쿠키 삭제 등)
                    response.setStatus(HttpServletResponse.SC_OK);
                    //BaseResponse에 담아 보내기
                    BaseResponse<String> dto = BaseResponse.onSuccess("Successfully logged out");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
                })
        );
        
        //로그인 커스텀 필터 등록
        http.addFilterAt(new LoginFilter(authConfiguration.getAuthenticationManager(), validator), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
