package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.email.EmailService;
import com.example.monthlylifebackend.payment.dto.DailySettlementDto;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.payment.dto.res.GetPaymentMethodRes;
import com.example.monthlylifebackend.payment.service.BillingKeyService;
import com.example.monthlylifebackend.payment.service.PaymentService;
import com.example.monthlylifebackend.payment.service.SettlementService;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Facade
@RequiredArgsConstructor
@Slf4j
@EnableScheduling
public class PaymentFacade {
    private final PaymentService paymentService;
    private final BillingKeyService billingKeyService;
    private final SubscribeService subscribeService;
    private final UserService userService;
    private final SettlementService settlementService;
    private final EmailService emailService;


    public Long createPaymentMethod(PostBillingKeyReq dto, User user) {
        //빌링키를 포트원에 확인하고 정보 받아오기
        return billingKeyService.createPaymentMethod(dto, user);
    }

    public Page<GetPaymentMethodRes> getPaymentMethodResPage(User user, int page, int size) {
        return billingKeyService.getPaymentMethodByUser(user, page, size);
    }

    @Transactional
    public void startPayment(PostBillingKeyReq dto, User user) {

        Subscribe subscribe = subscribeService.getSubscribeByIdx(1L);
        //구독번호와 가격을 이용해서 첫번째 결제하기
        paymentService.startPayment(dto.getBillingKey(), subscribe);

//        //빌링 키 저장
//        billingKeyService.createBillingKey(dto, user);
    }

    @Transactional
    public void getWebhook(PostWebhookReq dto) {

        //포트원에 한번 검증하고 올바르면 ok로 저장
        String paymentId = paymentService.getWebhook(dto);

        Long subscribeIdx = extractSubscribeIdx(paymentId);
        Integer cycle = extractNextCycle(paymentId);

        //구독을 확인하고 다음달 결제할 가격 설정
        Subscribe subscribe = subscribeService.getSubscribeByIdx(subscribeIdx);

        //빌링 키 조회
        String billingKey = billingKeyService.getBillingKey(subscribe.getBillingKey().getIdx(), subscribe.getUser());

        //결제 가격 조회
        Long nextPrice = subscribeService.calcPriceCycle(subscribe, cycle);

        //다음달 결제를 해야하면 결제 예약
        if (nextPrice == 0) return;

        paymentService.schedulePayment(subscribe, billingKey, nextPrice, cycle);
    }

    @Transactional
    @Scheduled(cron = "0 0 1 * * *", zone = "Asia/Seoul")
    public void payout() {
        LocalDateTime today = LocalDate.now().atStartOfDay().plusHours(15);
        LocalDateTime yesterday = today.minusDays(1);
        log.info(yesterday + "schedule start");

        DailySettlementDto dto = paymentService.DailySettlement(yesterday, today);

        settlementService.save(dto.getSettlement());
        subscribeService.saveDelayedSubscribeList(dto.getDelayedSubscribeList());
        userService.saveDelayedUserList(dto.getDelayedUserList());

        for(Subscribe subscribe : dto.getDelayedSubscribeList()) {
            emailService.sendDelayEmail(subscribe.getUser(), subscribe);
        }


        log.info(yesterday + "schedule end");
    }





    private Long extractSubscribeIdx(String str) {
        String[] parts = str.split("-");
        return Long.parseLong(parts[1]);
    }
    private Integer extractNextCycle(String str) {
        String[] parts = str.split("-");
        return Integer.parseInt(parts[2]) + 1;
    }

    public void check(String billingKey) {
        paymentService.checkBillingKey(billingKey);
    }
}