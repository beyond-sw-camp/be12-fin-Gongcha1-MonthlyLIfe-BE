package com.example.monthlylifebackend.cart.dto;

public interface GetCartListProjection {

    Long getCartidx();
    Long getSaleidx();
    String getSalename();
    int getPeriod();
    int getPrice();
    String getProductCode();
    String getProductImgurl();
}
