package com.example.monthlylifebackend.subscribe.facade;


import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SubscribeFacade
{


    private final SubscribeService subscribeService;


    public List<GetSubscribeRes> getSubscriptionInfo(User user) {
        return subscribeService.getSubscriptionInfo(user);
    }
}
