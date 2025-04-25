package com.example.monthlylifebackend.subscribe.facade;


import com.example.monthlylifebackend.admin.service.ItemService;
import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.common.exception.handler.PaymentHandler;
import com.example.monthlylifebackend.payment.service.BillingKeyService;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeListRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_ALLOWED_USER;

@Facade
@RequiredArgsConstructor
public class SubscribeFacade {

    private final SubscribeService subscribeService;
    private final PaymentService paymentService;
    private final BillingKeyService billingKeyService;
    private final ItemService itemService;


    public Page<GetSubscribeListRes> getSubscriptionInfo(User user, Pageable pageable) {
        return subscribeService.getSubscriptionInfo(user.getId(), pageable);
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

        //구독 생성
        Subscribe subscribe = subscribeService.createSubscription(reqDto,user);
        //재고 수량 변경
        itemService.subscribeItem(subscribe);
        //결제 생성
        String key = billingKeyService.getBillingKey(subscribe.getBillingKey().getIdx(), user);


        //결제 시작
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



    @Transactional
    public void extendSubscription(Long detailIdx, PostExtendRequest dto, User user) {
        subscribeService.extendSubscription(detailIdx,user.getId() , dto);
    }

    @Transactional
    public void createReport(PostRepairOrLostReq req, User user) {
        subscribeService.createRepairOrLost(req  , user.getId());
    }
}
