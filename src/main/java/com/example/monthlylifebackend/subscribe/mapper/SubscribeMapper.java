package com.example.monthlylifebackend.subscribe.mapper;


import com.example.monthlylifebackend.product.model.Sale;
import com.example.monthlylifebackend.product.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.model.Payment;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface SubscribeMapper {




    @Mapping(source = "sale.name", target = "saleName")
    @Mapping(source = "salePrice.period", target = "period")
    @Mapping(source = "salePrice.price", target = "price")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    GetSubscribePageResDto getSubscriptionResDto(Sale sale, SalePrice salePrice, User user);


    @Mapping(source = "user", target = "user")
    @Mapping(source = "payment", target = "payment")
    Subscribe tosubscribe(User user , Payment payment);



    @Mapping(source = "dto.sale_idx", target = "sale.idx")  // sale_idx를 Sale 객체의 idx로 매핑
    @Mapping(source = "dto.start_at", target = "start_at")
    @Mapping(source = "subscribe", target = "subscribe")
    @Mapping(target = "endAt", expression = "java(calculateEndAt(dto.getStart_at(), dto.getPeriod()))")  // endAt 계산
    SubscribeDetail tosubscribedetail(Subscribe subscribe ,PostRentalDeliveryReqDto dto);

    // 개월 수 더하는 로직
    default LocalDateTime calculateEndAt(LocalDateTime start_at, int period) {
        return start_at.plusMonths(period);  // period만큼 월을 더해 계산
    }





    @Mapping(source = "dto.recipientName", target = "recipientName")
    @Mapping(source = "dto.recipientPhone", target = "recipientPhone")
    @Mapping(source = "dto.postalCode", target = "postalCode")
    @Mapping(source = "dto.address1", target = "address1")
    @Mapping(source = "dto.address2", target = "address2")
    @Mapping(source = "dto.status", target = "status")
    @Mapping(source = "dto.courierCompany", target = "courierCompany")
    @Mapping(source = "dto.trackingNumber", target = "trackingNumber")
    @Mapping(source = "dto.deliveryMemo", target = "deliveryMemo")
    @Mapping(source = "dto.shippedAt", target = "shippedAt")
    @Mapping(source = "dto.deliveredAt", target = "deliveredAt")
    @Mapping(source = "subscribeDetail", target = "subscribeDetail") // 예시: subscribeDetail 설정은 별도로 처리
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())") // 생성시간 자동 설정
    @Mapping(target = "updatedAt", expression = "java(java.time.LocalDateTime.now())") // 업데이트 시간 자동 설정
    RentalDelivery toRentalDelivery(PostRentalDeliveryReqDto dto ,SubscribeDetail subscribeDetail);


}
