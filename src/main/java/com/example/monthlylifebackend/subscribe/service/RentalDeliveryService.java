package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.RentalDeliveryHandler;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.RentalStatus;
import com.example.monthlylifebackend.subscribe.repository.RentalDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class RentalDeliveryService {
    private final RentalDeliveryRepository rentalDeliveryRepository;
    public void updateDeliveryStatus(Long deliveryIdx) {
        RentalDelivery entity =  rentalDeliveryRepository.findById(deliveryIdx).orElseThrow(()->new RentalDeliveryHandler(ErrorStatus._NOT_FOUND_RENTALDELIVERY));
        //배송준비중(PREPARING) -> 배송중(SHIPPING)으로 상태 변경
        entity.updatedstatus(RentalStatus.SHIPPING);
        rentalDeliveryRepository.save(entity);
    }
}
