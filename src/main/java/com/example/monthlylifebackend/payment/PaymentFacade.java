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
    }
}
