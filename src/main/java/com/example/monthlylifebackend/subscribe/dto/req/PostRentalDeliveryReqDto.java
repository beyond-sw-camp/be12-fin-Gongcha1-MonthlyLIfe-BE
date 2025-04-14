package com.example.monthlylifebackend.subscribe.dto.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostRentalDeliveryReqDto {


    // 구독 디테일부분 받을거

    private List<ProductRequestDto> products;




    //렌탈


    private String recipientName;

    private String recipientPhone;

    private String postalCode;

    private String address1;

    private String address2;

    private String status;


    private String courierCompany;

    private String trackingNumber;

    private String deliveryMemo;

    private String shippedAt;

    private String deliveredAt;






}
