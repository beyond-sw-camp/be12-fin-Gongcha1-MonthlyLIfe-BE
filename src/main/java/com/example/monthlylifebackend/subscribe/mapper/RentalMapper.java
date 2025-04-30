package com.example.monthlylifebackend.subscribe.mapper;

import com.example.monthlylifebackend.subscribe.dto.SubscribeDetailDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetRentalDeliveryInfoRes;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.model.ReturnLocation;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring",imports={ReturnLocation.class, ReturnDeliveryStatus.class}) // Spring Bean으로 등록
public interface RentalMapper {


        @Mapping(target = "subscribeDetail", expression = "java(toSubscribeDetailDto(rentalDelivery.getSubscribeDetail()))")
        GetRentalDeliveryInfoRes toDto(RentalDelivery rentalDelivery);

        default SubscribeDetailDto toSubscribeDetailDto(SubscribeDetail subscribeDetail) {
                if (subscribeDetail == null) {
                        return null;
                }
                return SubscribeDetailDto.builder()
                        .idx(subscribeDetail.getIdx())
                        .build();
        }
}
