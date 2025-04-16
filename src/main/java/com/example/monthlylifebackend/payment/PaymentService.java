package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.exception.handler.PaymentHandler;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.payment.model.Payment;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import io.portone.sdk.server.payment.PayWithBillingKeyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._FAIL_PAYMENT;
import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_FOUND_PAYMENT;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    private final CustomPaymentClient customPaymentClient;
    public void startPayment(PostBillingKeyReq dto, Subscribe subscribe){
        //구독 번호 구하기
        String subscribeCode = subscribe.getIdx()+"";

        //결제 가격 구하기
        long price=0;
        for(SubscribeDetail detail: subscribe.getSubscribeDetailList()) {
            if(detail.getEndAt().isAfter(LocalDateTime.now())) continue;
            price += detail.getPrice();
        }

        //payment 번호 구하기
        Payment payment = createPayment(subscribe.getIdx(), price);

        //구독번호와 가격을 이용해서 첫번째 결제하기
        CompletableFuture<PayWithBillingKeyResponse> future = customPaymentClient.startPayment(payment.getPaymentId(), dto.getBillingKey(), subscribeCode, price);
        future.join();
        //결제 레포지토리에 결제 완료 저장
        payment.paySuccess();
        paymentRepository.save(payment);
    }

    //
    public String getWebhook(PostWebhookReq dto) {
        //결제 웹훅 아니면 오류
        if (!dto.getType().equals("TRANSACTION.PAID"))
            throw new PaymentHandler(_FAIL_PAYMENT);

        String webhookPaymentId = dto.getData().getPaymentId();
        //결제를 다시 포트원으로 보내서 확인
        Long total = customPaymentClient.getPaymentByPaymentId(webhookPaymentId);

        //DB의 결제내역 확인
        Payment payment = paymentRepository.findByPaymentId(webhookPaymentId).orElseThrow(() -> new PaymentHandler(_NOT_FOUND_PAYMENT));

        //포트원과 DB와 가격이 다르면 오류
        if (!Objects.equals(payment.getPrice(), total)) throw new PaymentHandler(_FAIL_PAYMENT);

        payment.paySuccess();
        paymentRepository.save(payment);

        return payment.getPaymentId();
    }

    public void schedulePayment(Subscribe subscribe, String billingKey, Long price) {
        Payment payment = createPayment(subscribe.getIdx(), price);
        customPaymentClient.OneMonthAfterPayment(payment.getPaymentId(), billingKey, subscribe.getIdx()+"", price, LocalDateTime.now());
    }

    private Payment createPayment(Long subscribeIdx, Long price) {
        String paymentId = "payment"+"-"+subscribeIdx+"-"+UUID.randomUUID();
        Payment payment = new Payment(paymentId, price);
        return paymentRepository.save(payment);
    }
}
