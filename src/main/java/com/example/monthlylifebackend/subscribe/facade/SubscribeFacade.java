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

import static com.example.monthlylifebackend.subscribe.model.SubscribeStatus.RETURN_REQUESTED;

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
        subscribeService.createReturnDelivery(userId, reqDto);
    }

    public GetSubscribeDetailInfoRes getReturnDelivery(String userId, Long detailId) {
        return subscribeService.getReturnDelivery(userId, detailId);
    }
}
