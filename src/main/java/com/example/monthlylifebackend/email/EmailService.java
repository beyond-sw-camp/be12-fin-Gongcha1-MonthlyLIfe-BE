package com.example.monthlylifebackend.email;

import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendMail(String email, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    public void sendDelayEmail(User user, Subscribe subscribe) {
        String subejct = "가구 구독 연체 안내문";

        StringBuilder delayedSubscribe = new StringBuilder();
        Long price = 0L;
        for(SubscribeDetail subscribeDetail : subscribe.getSubscribeDetailList()) {
            if(subscribeDetail.getEndAt().isBefore(LocalDateTime.now())) {
                delayedSubscribe.append(subscribeDetail.getSale().getName()).append(" ");
                price += subscribeDetail.getPrice();
            }
        }
        String delayedSubscribeStr = delayedSubscribe.toString();

        String text = String.format("""
                안녕하십니까 MonthlyLife 를 운영하는 Gongcha1입니다.
                저희 서비스를 이용해주셔서 진심으로 감사드립니다.
                현재 시스템상 정기 결제가 실패하여 안내드립니다.
                %s님의 구독 중인 상품
                %s에 대해서 연체가 진행중입니다.
                총 %d 원 연체 예정입니다.
                계좌번호 은행 112-213-123123로 %s `까지 보내주시기 바랍니다.
                문제 있으면 고객지원센터로 연락 주시기 바랍니다.
                감사합니다.
                """, user.getId(), delayedSubscribeStr, price, LocalDate.now().plusDays(7).toString());

        sendMail(user.getEmail(), subejct, text);

    }
}
