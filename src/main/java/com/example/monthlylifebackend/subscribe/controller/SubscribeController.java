package com.example.monthlylifebackend.subscribe.controller;

import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/subscribe")
@RequiredArgsConstructor
@Tag(name = "구독 서비스", description = "구독 생성, 취소, 연장, 결제 관리 등 API")
public class SubscribeController {

    private final SubscribeService subscribeService;


    @Operation(summary = "상품 구독 신청서", description = "상품을 구독하고 계약을 시작합니다.")
    @GetMapping("/subscribe")
    public GetSubscribePageResDto GetSubscription(@RequestParam("saleIdx") Long saleIdx,
                                                  @RequestParam("period") int period, @RequestParam("id") String id) {




        return subscribeService.getSubscription(id,saleIdx , period);
    }




    @Operation(summary = "상품 구독 생성", description = "상품을 구독하고 계약을 시작합니다.")
    @PostMapping("/subscribe")
    public void createSubscription(@RequestBody PostRentalDeliveryReqDto reqDto) {


        String id ="1";

        subscribeService.createSubscription(reqDto ,id);






    }

    @Operation(summary = "카트 상품 구독 생성", description = "상품을 구독하고 계약을 시작합니다.")
    @PostMapping("/subscribe/cart")
    public void createCartSubscription() {
        // 구독 생성 및 계약 시작 로직
    }


    @Operation(summary = "구독 취소 신청", description = "현재 구독을 취소 신청합니다.")
    @PostMapping("/cancel")
    public void cancelSubscription() {
        // 구독 취소 신청 로직
    }

    @Operation(summary = "구독 취소 철회", description = "취소된 구독을 다시 활성화합니다.")
    @PostMapping("/cancel/undo")
    public void undoCancelSubscription() {
        // 구독 취소 신청 취소 로직
    }

    @Operation(summary = "반납 신청", description = "구독한 상품의 반납 및 회수 요청을 보냅니다.")
    @PostMapping("/return")
    public void requestReturn() {
        // 반납 신청 로직
    }

    @Operation(summary = "자동 결제 설정 변경", description = "자동 결제 여부 설정 및 결제 수단 변경을 처리합니다.")
    @PostMapping("/payment/update")
    public void updateAutoPayment() {
        // 자동 결제 설정/변경 로직
    }

    @Operation(summary = "구독 연장", description = "구독 기간을 연장하고 계약을 갱신합니다.")
    @PostMapping("/extend")
    public void extendSubscription() {
        // 구독 연장 로직
    }

    @Operation(summary = "구독 정보 조회", description = "현재 구독 중인 상품의 정보를 확인합니다.")
    @GetMapping("/info")
    public void getSubscriptionInfo() {
        // 구독 정보 조회 로직
    }
}
