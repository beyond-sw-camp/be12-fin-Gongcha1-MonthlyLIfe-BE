package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import io.portone.sdk.server.PortOneClient;
import io.portone.sdk.server.common.Currency;
import io.portone.sdk.server.common.PaymentAmountInput;
import io.portone.sdk.server.payment.PayWithBillingKeyResponse;
import io.portone.sdk.server.payment.PaymentClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentClient portOneClient;
    public void startPayment(PostBillingKeyReq dto) {
        String paymentId = this.getNewPaymentId();
        String orderName="주문1";


        PaymentAmountInput paymentAmountInput = new PaymentAmountInput(
                29000,
                0L,
                0L
        );
        CompletableFuture<PayWithBillingKeyResponse> future =
            portOneClient.payWithBillingKey(
                    paymentId,
                    dto.getBillingKey(),
                    null,
                    orderName,
                    null,
                    null,
                    paymentAmountInput,
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

    private String getNewPaymentId() {
        return "payment"+UUID.randomUUID();
    }
}
