package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.payment.service.BillingKeyService;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Facade
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;
    private final BillingKeyService billingKeyService;
    private final SubscribeService subscribeService;

    @Transactional
    public void startPayment(PostBillingKeyReq dto, User user) {

        //구독번호와 가격을 이용해서 첫번째 결제하기
        paymentService.startPayment(dto, Subscribe.builder().idx(1L).build());
        //빌링 키 저장
        billingKeyService.createBillingKey(dto, user);
    }

    @Transactional
    public void getWebhook(PostWebhookReq dto) {

        //포트원에 한번 검증하고 올바르면 ok로 저장
        String paymentId = paymentService.getWebhook(dto);

        //구독을 확인하고 다음달 결제할 가격 설정
        Long subscribeIdx = extractMiddleAsLong(paymentId);

        Subscribe subscribe = Subscribe.builder().build();

        String billingKey = billingKeyService.getBillingKey(1L);
        Long nextPrice = 12000L;

        //다음달 결제를 해야하면 결제 예약
        if (nextPrice == 0) return;
        paymentService.schedulePayment(subscribe, billingKey, nextPrice);
    }

    private Long extractMiddleAsLong(String str) {
        String[] parts = str.split("-");
        return Long.parseLong(parts[1]);

    }

    public void check(String billingKey) {
        paymentService.checkBillingKey(billingKey);
    }
}