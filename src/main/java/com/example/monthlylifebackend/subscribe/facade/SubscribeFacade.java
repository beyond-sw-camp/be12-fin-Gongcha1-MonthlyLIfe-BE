package com.example.monthlylifebackend.subscribe.facade;


import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.req.PostReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
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

    private final SubscribeService subscribeService;


    public List<GetSubscribeRes> getSubscriptionInfo(User user) {
        return subscribeService.getSubscriptionInfo(user);
    }


    @Transactional
    public void returnDelivery(User user, PostReturnDeliveryReq reqDto) {
        subscribeService.createReturnDelivery(user.getId(), reqDto);
    }

    public GetSubscribeDetailInfoRes getReturnDelivery(User user, Long detailId) {
        return subscribeService.getReturnDelivery(user.getId(), detailId);
    }


    public void createSubscription(PostRentalDeliveryReqDto reqDto, User user) {
        subscribeService.createSubscription(reqDto,user.getId());

    }

    public GetSubscribePageResDto getSubscription(User user, Long saleidx, int period) {
        return subscribeService.getSubscription(user.getId()
                , saleidx, period);
    }

}
