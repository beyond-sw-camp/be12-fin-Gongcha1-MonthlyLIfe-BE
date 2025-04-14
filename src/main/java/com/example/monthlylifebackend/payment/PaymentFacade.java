package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class PaymentFacade {
    private final PaymentService paymentService;

    public void startPayment(PostBillingKeyReq dto){
        paymentService.startPayment(dto);
    }
}
