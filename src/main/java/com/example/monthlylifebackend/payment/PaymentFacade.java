package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Facade
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;


    public void startPayment(PostBillingKeyReq dto){

        //구독번호와 가격을 이용해서 첫번째 결제하기
        paymentService.startPayment(dto, Subscribe.builder().idx(1L).build());
    }

    public void getWebhook(PostWebhookReq dto) {

        //포트원에 한번 검증하고 올바르면 ok로 저장
        String paymentId = paymentService.getWebhook(dto);

        //구독을 확인하고 다음달 결제할 가격 설정
        Long nextPrice = 12000L;
        Subscribe subscribe = Subscribe.builder().idx(1L).build();
        String billingKey = "key";


        //다음달 결제를 해야하면 결제 예약
        if(nextPrice == 0) return;
        paymentService.schedulePayment(subscribe, billingKey, nextPrice);
    }
}
