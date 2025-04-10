package com.example.monthlylifebackend.config.filter;

import com.example.monthlylifebackend.auth.model.AuthUser;
import com.example.monthlylifebackend.user.dto.req.PostLoginReq;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       UsernamePasswordAuthenticationToken token;
        try {
            PostLoginReq user = new ObjectMapper().readValue(request.getInputStream(), PostLoginReq.class);
            token = new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword(), null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        AuthUser aUser = (AuthUser) auth.getPrincipal();
        String jwt = JwtUtil.generateToken(aUser.getUser());
        // logger.info("{}({})님에게 {} JWT 토큰 부여",user.getIdx(), user.getEmail(), jwt);
        ResponseCookie cookie = ResponseCookie.from("ATOKEN", jwt)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(3600) // 1시간(3600초) 유효시간
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setContentType("application/json");

        // 만약 로그인 시 정보가 필요하다면 반환
//        BaseResponse<LoginResp> dto = BaseResponse.success(LoginResp.of(user));
//        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
    }
}
