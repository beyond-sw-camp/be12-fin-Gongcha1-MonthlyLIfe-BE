package com.example.monthlylifebackend.subscribe.model;

public enum SubscribeStatus {
    //반납 상태
    SUBSCRIBING,       // 구독 중
    RETURN_REQUESTED,  // 반납 요청
    RETURNING,         // 반납 중
    CANCELED,           // 구독 해지
    REPAIR_REQUESTED, // 수리 요청
    RESERVED,// 구독 대기
    LOST // 분실
}
