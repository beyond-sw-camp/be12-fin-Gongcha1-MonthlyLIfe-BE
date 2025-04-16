package com.example.monthlylifebackend.subscribe.facade;


import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.subscribe.dto.req.PostReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.model.SubscribeStatus;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SubscribeFacade {
    private final SubscribeMapper subscribeMapper;

    private final SubscribeService subscribeService;


    public List<GetSubscribeRes> getSubscriptionInfo(User user) {
        return subscribeService.getSubscriptionInfo(user);
    }



    @Transactional
    public void returnDelivery(String userId, PostReturnDeliveryReq reqDto) {
        SubscribeDetail detail = subscribeService.getSubscribeDetailWithUserValidation(userId, reqDto.getSubscribedetailIdx());

        if (detail.getStatus().equals(SubscribeStatus.RETURN_REQUESTED)) {
            throw new RuntimeException("현재 반납 요청이 불가능한 상태입니다.");
        }

        detail.updateStatus(SubscribeStatus.RETURN_REQUESTED);

        subscribeService.createReturnDelivery(detail, reqDto);
    }


    public GetSubscribeDetailInfoRes getReturnDelivery(String userId, Long detailId) {
        SubscribeDetail detail = subscribeService.getSubscribeDetailWithUserValidation(userId, detailId);
        return subscribeMapper.toReturnDeliveryDto(detail);
    }
}
