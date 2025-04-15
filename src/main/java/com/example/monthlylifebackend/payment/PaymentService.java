package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import io.portone.sdk.server.common.BillingKeyPaymentInput;
import io.portone.sdk.server.common.Currency;
import io.portone.sdk.server.common.PaymentAmountInput;
import io.portone.sdk.server.payment.PayWithBillingKeyResponse;
import io.portone.sdk.server.payment.PaymentClient;
import io.portone.sdk.server.payment.paymentschedule.CreatePaymentScheduleResponse;
import io.portone.sdk.server.payment.paymentschedule.PaymentScheduleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentClient paymentClient;
    private final PaymentScheduleClient paymentScheduleClient;
    public void startPayment(PostBillingKeyReq dto, String subscribeCode, int price) {
        String paymentId = this.getNewPaymentId();

        BillingKeyPaymentInput input = getMinimumInput(dto.getBillingKey(), subscribeCode, price);


        CompletableFuture<PayWithBillingKeyResponse> future =
            paymentClient.payWithBillingKey(
                    paymentId,
                    dto.getBillingKey(),
                    null,
                    subscribeCode,
                    null,
                    null,
                    getAmount(price),
                    Currency.Krw.INSTANCE,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                    );
        future.thenAccept(response -> {
            System.out.println(response);
        });
    }
    public void OneMonthAfterPayment(PostBillingKeyReq dto, String subscribeCode, int price, LocalDateTime dateTime) {
        schedulePayment(dto, subscribeCode, price, dateTime.plusMinutes(1));
    }

    private void schedulePayment(PostBillingKeyReq dto, String subscribeCode, int price, LocalDateTime dateTime) {
        String paymentId = this.getNewPaymentId();

        Instant time = dateTime.atZone(ZoneId.systemDefault()).toInstant();

        BillingKeyPaymentInput input = getMinimumInput(dto.getBillingKey(), subscribeCode, price);

        CompletableFuture<CreatePaymentScheduleResponse> future =
                paymentScheduleClient.createPaymentSchedule(paymentId, input, time);

        future.whenComplete((response, throwable) -> {
            if (throwable != null) {
                // 예외 처리
                throwable.printStackTrace();
            } else {
                // 응답 처리
                System.out.println("결제 예약 성공: " + response);
            }
        });
    }

    private BillingKeyPaymentInput getMinimumInput(String billingKey, String orderName, int price) {
        return  new BillingKeyPaymentInput(
                null,
                billingKey,
                null,
                orderName,
                null,
                null,
                getAmount(price),
                Currency.Krw.INSTANCE,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private PaymentAmountInput getAmount(int price) {
        return new PaymentAmountInput(
                price,
                0L,
                0L
        );
    }


    private String getNewPaymentId() {
        return "payment"+UUID.randomUUID();
    }
}
