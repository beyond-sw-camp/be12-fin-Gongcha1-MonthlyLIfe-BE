package com.example.monthlylifebackend.sale.dto.res;


import com.example.monthlylifebackend.sale.dto.req.PostSaleRegisterReq;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBestSaleRes {
    private Long   saleIdx;
    private String name;
    private Long   categoryIdx;
    private String ImageUrl;    // 첫 번째 상품 이미지
    private String manufacturer;    // 제조사
    private String conditionName;   // 등급 이름
    private int    price;        // 최저가
    private int    period;          // 해당 최저가 기간
    private Long   subscribeCount;  // 구독 수
}

