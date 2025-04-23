package com.example.monthlylifebackend.config.filter;

import com.example.monthlylifebackend.auth.model.AuthUser;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.common.code.BaseErrorCode;
import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.user.dto.req.PostLoginReq;
import com.example.monthlylifebackend.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import jakarta.validation.Validator;
import java.io.IOException;
import java.util.Set;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final Validator validator;

    public LoginFilter(AuthenticationManager authenticationManager, Validator validator) {
        super(authenticationManager);
        this.validator=validator;
        setFilterProcessesUrl("/auth/login");
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        } else {
            UsernamePasswordAuthenticationToken token;
            try {
                PostLoginReq dto = new ObjectMapper().readValue(request.getInputStream(), PostLoginReq.class);

                Set<ConstraintViolation<PostLoginReq>> violations = validator.validate(dto);

                if (!violations.isEmpty()) {
                    // 예: 첫 번째 에러만 뽑아서 메시지 던지기
                    String message = violations.iterator().next().getMessage();
                    sendErrorResponse(response, ErrorStatus._BAD_REQUEST, message);
                    throw new AuthenticationServiceException(message);
                }

                token = new UsernamePasswordAuthenticationToken(dto.getId(), dto.getPassword(), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return this.getAuthenticationManager().authenticate(token);
        }

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException, ServletException {
        AuthUser aUser = (AuthUser) auth.getPrincipal();
        String jwt = JwtUtil.generateToken(aUser.getUser());

        ResponseCookie cookie = ResponseCookie.from("ATOKEN", jwt)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .maxAge(3600) // 1시간(3600초) 유효시간
                .build();
        response.setHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        response.setContentType("application/json");

        // 만약 로그인 시 정보가 필요하다면 반환
        BaseResponse<String> dto = BaseResponse.onSuccess(aUser.getUser().getId());
        response.getWriter().write(new ObjectMapper().writeValueAsString(dto));
    }

    private void sendErrorResponse(HttpServletResponse response, BaseErrorCode errorCode, String details) {
        response.setStatus(errorCode.getReasonHttpStatus().getHttpStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        BaseResponse<Object> errorResponse = BaseResponse.onFailure(errorCode, details);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.writeValue(response.getWriter(), errorResponse);
        } catch (IOException ex) {
            throw new RuntimeException("응답 JSON 변환 실패", ex);
        }
    }
}
