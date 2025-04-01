package com.example.monthlylifebackend.subscribe.controller;


import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class SubscribeController {


    private final SubscribeService subscribeService;



    //todo 상품 구독 (구독 생성 및 계약 시작) 우선순위 1
    @PostMapping("/create")
    public void createSubscription() {
        // 구독 생성 및 계약 시작 로직 구현
    }

    //todo 구독 취소 신청 우선순위 2
    @PostMapping("/cancel")
    public void cancelSubscription() {
        // 구독 취소 신청 로직 구현
    }

    //todo 구독 취소 신청 취소 우선순위 4
    @PostMapping("/cancel/undo")
    public void undoCancelSubscription() {
        // 구독 취소 신청 취소 로직 구현
    }

    //todo 반납 신청 우선순위 1
    @PostMapping("/return")
    public void requestReturn() {
        // 반납 신청 및 회수 요청 로직 구현
    }

    //todo 자동 결제 설정/결제 수단 변경 우선순위 1
    @PostMapping("/payment/update")
    public void updateAutoPayment() {
        // 자동 결제 설정 및 결제 수단 변경 로직 구현
    }

    //todo 상품 구독 연장 우선순위 3
    @PostMapping("/extend")
    public void extendSubscription() {
        // 구독 연장 및 계약 기간 갱신 로직 구현
    }

    //todo 구독 정보 확인 우선순위 1
    @GetMapping("/info")
    public void getSubscriptionInfo() {
        // 현재 구독 중인 상품 정보 및 상태 조회 로직 구현
    }






}
