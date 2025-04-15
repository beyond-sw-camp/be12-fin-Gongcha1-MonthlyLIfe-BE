package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Facade
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;

    public void startPayment(PostBillingKeyReq dto){

        //구독 번호 구하기
        String subscribeCode = "sub1";
        int price = 29000;
        //구독번호와 가격을 이용해서 첫번째 결제하기
        paymentService.startPayment(dto, subscribeCode, price);

        //다음달 결제금액 구하기

        int nextPrice = 28000;
        System.out.println("nextPay");
        //다음달 결제하기
        paymentService.OneMonthAfterPayment(dto, subscribeCode, nextPrice, LocalDateTime.now());
    }
}
