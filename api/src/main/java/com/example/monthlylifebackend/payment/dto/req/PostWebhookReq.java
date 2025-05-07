package com.example.monthlylifebackend.payment.dto.req;

import lombok.Getter;

import java.time.Instant;

@Getter
public class PostWebhookReq {
    private String type;
    private Instant timestamp;
    private PostWebhookDataReq data;
}
