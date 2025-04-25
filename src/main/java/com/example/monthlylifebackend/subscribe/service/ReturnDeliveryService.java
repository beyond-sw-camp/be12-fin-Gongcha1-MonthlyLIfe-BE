package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.ReturnDeliveryHandler;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDelivery;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.model.ReturnLocation;
import com.example.monthlylifebackend.subscribe.repository.RepairRequestRepository;
import com.example.monthlylifebackend.subscribe.repository.ReturnDeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReturnDeliveryService {
    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final RepairRequestRepository repairRequestRepository;

    public Page<GetAdminReturnDeliveryRes> getReturnRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
        String[] parts = String.valueOf(status).split("_");
        String part = parts[0];
        return returnDeliveryRepository.findReturnRequestByFilter(pageable, part, from, to);
    }


    public Page<GetAdminReturnDeliveryRes> getRePairRequestList(Pageable pageable, ReturnDeliveryStatus status, LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;
        String[] parts = String.valueOf(status).split("_");
        String part = parts[0];
        return returnDeliveryRepository.findRepairRequestByFilter(pageable, part, from, to);
    }

    public void updateReturnRequst(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx)
                .orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));
        delivery.updateReturnLocation(ReturnLocation.valueOf(dto.getReturnLocation()));
        delivery.updateStatus(ReturnDeliveryStatus.RETURN_ACCEPT);
        returnDeliveryRepository.save(delivery);
        returnDeliveryRepository.save(delivery);
    }

    public void updateRepairRequest(Long returnDeliveryIdx, PostAdminReturnDeliveryReq dto) {
        ReturnDelivery delivery = returnDeliveryRepository.findById(returnDeliveryIdx)
                .orElseThrow(() -> new ReturnDeliveryHandler(ErrorStatus._NOT_FOUND_RETURNDELIVERY));
        delivery.updateReturnLocation(ReturnLocation.valueOf(dto.getReturnLocation()));
        delivery.updateStatus(ReturnDeliveryStatus.REPAIR_ACCEPT);
        returnDeliveryRepository.save(delivery);
    }
}
