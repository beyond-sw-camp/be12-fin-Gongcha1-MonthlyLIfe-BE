package com.example.monthlylifebackend.payment;

import io.portone.sdk.server.common.BillingKeyPaymentInput;
import io.portone.sdk.server.common.Currency;
import io.portone.sdk.server.common.PaymentAmountInput;
import io.portone.sdk.server.payment.PayWithBillingKeyResponse;
import io.portone.sdk.server.payment.Payment;
import io.portone.sdk.server.payment.PaymentAmount;
import io.portone.sdk.server.payment.PaymentClient;
import io.portone.sdk.server.payment.billingkey.BillingKeyClient;
import io.portone.sdk.server.payment.billingkey.BillingKeyInfo;
import io.portone.sdk.server.payment.paymentschedule.CreatePaymentScheduleResponse;
import io.portone.sdk.server.payment.paymentschedule.PaymentScheduleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class CustomPaymentClient {
    private final PaymentClient paymentClient;
    private final PaymentScheduleClient paymentScheduleClient;
    private final BillingKeyClient billingKeyClient;

    //paymentId로 결제 가격 가져오기
    public Long getPaymentByPaymentId(String paymentId) {
        Payment payment = paymentClient.getPayment(paymentId).join();
        Payment.Recognized recognized = (Payment.Recognized) payment;
        PaymentAmount amount = recognized.getAmount();
        return amount.getTotal();
    }

    public BillingKeyInfo checkBillingKey(String billingKey) {
        return billingKeyClient.getBillingKeyInfo(billingKey).join();
    }

    // 빌링키로 결제하기
    public CompletableFuture<PayWithBillingKeyResponse> startPayment(String paymentId, String billingKey, String subscribeCode, Long price) {

        return paymentClient.payWithBillingKey(
                    paymentId,
                    billingKey,
                    null,
                    subscribeCode,
                    null,
                    null,
                    createAmount(price),
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

    //빌링키로 한달뒤 결제 예약 걸기
    public CompletableFuture<CreatePaymentScheduleResponse> OneMonthAfterPayment(String paymentId, String billingKey, String subscribeCode, Long price, LocalDateTime dateTime) {
        return schedulePayment(paymentId, billingKey, subscribeCode, price, dateTime.plusMinutes(1));
    }

    //빌링키로 결제 예약 걸기
    private CompletableFuture<CreatePaymentScheduleResponse> schedulePayment(String paymentId,String billingKey, String subscribeCode, Long price, LocalDateTime dateTime) {

        Instant time = dateTime.atZone(ZoneId.systemDefault()).toInstant();

        BillingKeyPaymentInput input = getMinimumInput(billingKey, subscribeCode, price);

        return paymentScheduleClient.createPaymentSchedule(paymentId, input, time);
    }

    private BillingKeyPaymentInput getMinimumInput(String billingKey, String orderName, Long price) {
        return  new BillingKeyPaymentInput(
                null,
                billingKey,
                null,
                orderName,
                null,
                null,
                createAmount(price),
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

    private PaymentAmountInput createAmount(Long price) {
        return new PaymentAmountInput(
                price,
                0L,
                0L
        );
    }
}
