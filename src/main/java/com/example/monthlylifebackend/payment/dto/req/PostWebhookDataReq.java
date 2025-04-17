package com.example.monthlylifebackend.payment.dto.req;

import lombok.Getter;

@Getter
public class PostWebhookDataReq {
    private String paymentId;
    private String storeId;
    private String transactionId;
}
