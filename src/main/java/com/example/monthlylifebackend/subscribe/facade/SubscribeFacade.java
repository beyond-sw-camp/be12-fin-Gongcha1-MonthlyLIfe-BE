package com.example.monthlylifebackend.subscribe.facade;


import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.payment.service.BillingKeyService;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.req.PostReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.req.PostSubscribeReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeListRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Facade
@RequiredArgsConstructor
public class SubscribeFacade {

    private final SubscribeService subscribeService;
    private final PaymentService paymentService;
    private final BillingKeyService billingKeyService;
    private final UserService userService;


    public Page<GetSubscribeListRes> getSubscriptionInfo(User user, Pageable pageable) {
        return subscribeService.getSubscriptionInfo(user.getId() ,pageable);
    }


    @Transactional
    public void returnDelivery(User user, PostReturnDeliveryReq reqDto) {
        subscribeService.createReturnDelivery(user.getId(), reqDto);
    }

    public GetSubscribeDetailInfoRes getReturnDelivery(User user, Long detailId) {
        return subscribeService.getReturnDelivery(user.getId(), detailId);
    }


    @Transactional
    public Long createSubscription(PostSubscribeReq reqDto, User user) {
        Subscribe subscribe = subscribeService.createSubscription(reqDto,user);

        String key = billingKeyService.getBillingKey(subscribe.getBillingKey().getIdx());

        paymentService.startPayment(key, subscribe);

        return subscribe.getIdx();
    }

    public GetSubscribePageResDto getSubscription(User user, Long saleidx, int period) {
        return subscribeService.getSubscription(user.getId()
                , saleidx, period);
    }

    @Transactional
    public void undoCancleSubscription(User user, Long detailIdx) {
         subscribeService.undoCancleSubscription(user.getId(), detailIdx);
    }
}
