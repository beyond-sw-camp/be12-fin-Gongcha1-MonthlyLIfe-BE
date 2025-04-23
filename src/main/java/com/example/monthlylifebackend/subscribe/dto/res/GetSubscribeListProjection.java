package com.example.monthlylifebackend.subscribe.dto.res;

import java.time.LocalDateTime;

public interface GetSubscribeListProjection {

    Long getSubscribeIdx();
    Long getSubscribeDetailIdx();
    Long getSaleidx();
    String getSalename();
    int getPeriod();
    int getPrice();
    String getStatus();
    String getProductCode();
    String getProductImgurl();
    LocalDateTime getCreated_at();
    LocalDateTime getStartAt();
    LocalDateTime getEndAt();
}
