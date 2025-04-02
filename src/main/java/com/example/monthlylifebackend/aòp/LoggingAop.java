package com.example.monthlylifebackend.aòp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAop {

    private static final Logger log = LoggerFactory.getLogger(LoggingAop.class);

    // 모든 컨트롤러 메서드에 적용
    @Pointcut("execution(* com.example.monthlylifebackend.*Controller.*(..))")
    public void controllerPointcut() {
    }

    @Around("controllerPointcut()") // 요청 및 응답을 한 번에 처리
    public Object logHttpRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        // 현재 요청 정보 가져오기 (RequestContextHolder 사용)
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return joinPoint.proceed(); // 요청 정보가 없으면 그냥 진행
        }

        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        StopWatch stopWatch = new StopWatch();
        stopWatch.start(); // 실행 시간 측정 시작

        // 요청 정보 수집
        String remoteHost = request.getRemoteAddr();
        String username = request.getRemoteUser() != null ? request.getRemoteUser() : "-";
        String requestLine = request.getMethod() + " " + request.getRequestURI() + " " + request.getProtocol();
        String timestamp = new SimpleDateFormat("yyyy/MM/dd:HH:mm:ss Z").format(new Date());

        // 요청 로그 출력
        log.info("{} - {} [{}] \"{}\" - {}", remoteHost, username, timestamp, requestLine, "REQUEST_RECEIVED");

        int responseCode = 500; // 기본 응답 코드 (예외 발생 시 500 반환)

        try {
            // 컨트롤러 메서드 실행
            Object result = joinPoint.proceed();

            // 정상적인 경우 응답 코드 업데이트
            if (response != null) {
                responseCode = response.getStatus();
            }

            return result;

        } catch (Exception e) {
            // 예외 발생 시 로그 남기기

            log.error("Exception occurred while processing request: {} {} {}", requestLine, e.getMessage(), e);
            throw e; // 예외 발생 시 기본적으로 null 반환 (혹은 다른 처리 가능)

        } finally {
            stopWatch.stop(); // 실행 시간 측정 종료

            // 응답 로그 출력 (예외 발생 시에도 반드시 실행)
            log.info("{} - {} [{}] \"{}\" {} ({}ms)",
                    remoteHost, username, timestamp, requestLine, responseCode, stopWatch.getTotalTimeMillis());
        }
    }
}
