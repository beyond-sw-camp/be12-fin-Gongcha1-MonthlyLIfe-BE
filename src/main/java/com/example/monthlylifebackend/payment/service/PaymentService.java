package com.example.monthlylifebackend.payment.service;

import com.example.monthlylifebackend.common.exception.handler.PaymentHandler;
import com.example.monthlylifebackend.payment.CustomPaymentClient;
import com.example.monthlylifebackend.payment.dto.DailySettlementDto;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.dto.res.GetAdminRecentPaymentRes;
import com.example.monthlylifebackend.payment.model.Payment;
import com.example.monthlylifebackend.payment.model.Settlement;
import com.example.monthlylifebackend.payment.repository.PaymentRepository;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.user.model.User;
import io.portone.sdk.server.payment.PayWithBillingKeyResponse;
import io.portone.sdk.server.payment.billingkey.BillingKeyInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._NOT_FOUND_PAYMENT;
import static com.example.monthlylifebackend.common.code.status.ErrorStatus._PAYMENT_FAILED;
import static com.example.monthlylifebackend.user.service.UserService.fillMonthlyIntData;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final CustomPaymentClient customPaymentClient;
    public void startPayment(String billingKey, Subscribe subscribe){
        //구독 번호 구하기
        String subscribeCode = subscribe.getIdx()+"";

        //결제 가격 구하기
        long price=0;
        for(SubscribeDetail detail: subscribe.getSubscribeDetailList()) {
            if(detail.getEndAt().isBefore(LocalDateTime.now())) continue;
            price += detail.getPrice();
        }

        //payment 번호 구하기
        Payment payment = createPayment(subscribe.getIdx(), price, 1, LocalDateTime.now());

        //구독번호와 가격을 이용해서 첫번째 결제하기
        CompletableFuture<PayWithBillingKeyResponse> future = customPaymentClient.startPayment(payment.getPaymentId(), billingKey, subscribeCode, price);
        future.join();
        //결제 레포지토리에 결제 완료 저장
        payment.paySuccess();
        paymentRepository.save(payment);
    }



    //
    public String getWebhook(PostWebhookReq dto) {
        //결제 웹훅 아니면 오류
        String s = dto.getType();
        if (!dto.getType().equals("Transaction.Paid"))
            throw new PaymentHandler(_PAYMENT_FAILED);

        String webhookPaymentId = dto.getData().getPaymentId();
        //결제를 다시 포트원으로 보내서 확인
        Long total = customPaymentClient.getPaymentByPaymentId(webhookPaymentId);

        //DB의 결제내역 확인
        Payment payment = paymentRepository.findByPaymentId(webhookPaymentId).orElseThrow(() -> new PaymentHandler(_NOT_FOUND_PAYMENT));

        //포트원과 DB와 가격이 다르면 오류
        if (!Objects.equals(payment.getPrice(), total)) throw new PaymentHandler(_PAYMENT_FAILED);

        payment.paySuccess();
        paymentRepository.save(payment);

        return payment.getPaymentId();
    }

    public void schedulePayment(Subscribe subscribe, String billingKey, Long price, Integer cycle) {
        Payment payment = createPayment(subscribe.getIdx(), price, cycle, LocalDateTime.now().plusMonths(1));
        customPaymentClient.OneMonthAfterPayment(payment.getPaymentId(), billingKey, subscribe.getIdx()+"", price, LocalDateTime.now());
    }

    public Payment createPayment(Long subscribeIdx, Long price, int cycle, LocalDateTime scheduledAt) {
        String paymentId = "payment"+"-"+subscribeIdx+"-"+cycle+"-"+UUID.randomUUID();
        Subscribe s = Subscribe.builder().idx(subscribeIdx).build();
        s.ignoreVersion();
        Payment payment = new Payment(paymentId, price, s, scheduledAt);
        return paymentRepository.save(payment);
    }

    public void checkBillingKey(String billingKey) {
        BillingKeyInfo billingKeyInfo = customPaymentClient.checkBillingKey(billingKey);

    }

    public Page<GetAdminPaymentRes> getPayments(int page, int size, String searchType, String searchQuery, LocalDate dateFrom,LocalDate dateTo, boolean overdueOnly) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        LocalDateTime dateFromTime = null;
        LocalDateTime dateToTime = null;

        if (dateFrom != null){
            dateFromTime = dateFrom.atStartOfDay();
        }

        if (dateTo != null){
         dateToTime = dateTo.atStartOfDay();
        }

        return paymentRepository.findAdminPayments(pageable, searchType, searchQuery, dateFromTime, dateToTime, overdueOnly);
    }

    public Long sumTotalPaid() {
        int thisYear = LocalDate.now().getYear();

        return paymentRepository.sumTotalPaid(thisYear);

    }

    public List<Integer> getAdminStatistics() {
        //올해
        int thisYear = LocalDate.now().getYear();

        List<Object[]> rawData = paymentRepository.getMonthlySalesRaw(thisYear);
        Map<Integer, Long> salesMap = new HashMap<>();

        for (Object[] row : rawData) {
            Integer month = Integer.parseInt(row[0].toString()); // "3" → 3
            Long total = Long.parseLong(row[1].toString()); // "100000" → 100000
            salesMap.put(month, total);
        }


        return fillMonthlyIntData(salesMap);
    }

    public List<GetAdminRecentPaymentRes> getAdminRecentPaymentRes() {
        return paymentRepository.findRecentPayments(PageRequest.of(0, 5));
    }

    public DailySettlementDto DailySettlement(LocalDateTime start, LocalDateTime end) {

        //오늘 일자 결제내역들 불러오기
        List<Payment> payments = paymentRepository.findAllByScheduledAtGreaterThanEqualAndScheduledAtLessThan(start, end);

        Long totalAmount = 0L;
        int paymentCount = 0;
        List<Subscribe> delayedSubscribeList = new ArrayList<>();
        Set<User> delayedUserSet = new HashSet<>();

        for(Payment payment : payments) {
            //결제가 되었으면 정산
            if(payment.isPaid()) {
                totalAmount += payment.getPrice();
                paymentCount++;
            }
            //안되었으면 연체 처리
            else {
                delayedSubscribeList.add(payment.getSubscribe());
                delayedUserSet.add(payment.getSubscribe().getUser());
            }
        }

        List<User> delayedUserList = delayedUserSet.stream().toList();

        Settlement settlement = Settlement.builder()
                .settlementDate(LocalDate.from(start))
                .totalSalesAmount(totalAmount)
                .totalRefundAmount(0L)
                .netAmount(totalAmount)
                .transactionCount(paymentCount)
                .build();


        return DailySettlementDto.builder()
                .settlement(settlement)
                .delayedSubscribeList(delayedSubscribeList)
                .delayedUserList(delayedUserList)
                .build();
    }
}
