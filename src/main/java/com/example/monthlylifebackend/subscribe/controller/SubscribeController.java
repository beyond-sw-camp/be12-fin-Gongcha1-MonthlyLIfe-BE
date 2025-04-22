package com.example.monthlylifebackend.subscribe.controller;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeListRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.facade.SubscribeFacade;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/subscribe")
@RequiredArgsConstructor
@Tag(name = "구독 서비스", description = "구독 생성, 취소, 연장, 결제 관리 등 API")
public class SubscribeController {


    private final SubscribeFacade subscribeFacade;


    @Operation(summary = "상품 구독 신청서", description = "상품을 구독하고 계약을 시작합니다.")
    @GetMapping("/subscribe")
    public BaseResponse<GetSubscribePageResDto> GetSubscription(@RequestParam("saleIdx") Long saleIdx,
                                                                @RequestParam("period") int period,
                                                                @AuthenticationPrincipal @Valid @NotNull User user) {

        GetSubscribePageResDto rs = subscribeFacade.getSubscription(user, saleIdx, period);
        return BaseResponse.onSuccess(rs);
    }


    @Operation(summary = "상품 구독 생성", description = "상품을 구독하고 계약을 시작합니다.")
    @PostMapping("/subscribe")
    public BaseResponse<Long> createSubscription(@RequestBody PostSubscribeReq reqDto,
                                           @AuthenticationPrincipal User user) {

        Long idx = subscribeFacade.createSubscription(reqDto, user);
        return BaseResponse.onSuccess(idx);

    }


    @PostMapping("/report")
    public BaseResponse<?> report(
            @RequestBody PostRepairOrLostReq req/*@AuthenticationPrincipal User user*/
    ) {
        // 간단한 필드 검증

        User user = User.builder().id("1").build();

        System.out.println(req.getImageUrls().size());
        // Service에 위임
        subscribeFacade.createReport(
                req,
                user
        );
        return BaseResponse.onSuccess(null);
    }





    @Operation(summary = "구독 취소 신청", description = "현재 구독을 취소 신청합니다.")
    @PostMapping("/cancel")
    public void cancelSubscription() {
        // 구독 취소 신청 로직
    }

    @Operation(summary = "구독 취소 철회", description = "취소된 구독을 다시 활성화합니다.")
    @PostMapping("/{detailIdx}/cancel/undo")
    public BaseResponse undoCancelSubscription(/*@AuthenticationPrincipal User user,*/ @PathVariable Long detailIdx) {
        User user = User.builder().id("1").build();
        // 구독 취소 신청 취소 로직

        subscribeFacade.undoCancleSubscription(user ,detailIdx);
        return BaseResponse.onSuccess(null);

    }

    @Operation(summary = "반납 신청", description = "구독한 상품의 반납 및 회수 요청을 보냅니다.")
    @PostMapping("/return")
    public BaseResponse requestReturn(/*@AuthenticationPrincipal User user,*/@RequestBody PostReturnDeliveryReq reqDto) {


        // Todo 삭제할것
        User user = User.builder().id("1").build();

        subscribeFacade.returnDelivery(user, reqDto);
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "자동 결제 설정 변경", description = "자동 결제 여부 설정 및 결제 수단 변경을 처리합니다.")
    @PostMapping("/payment/update")
    public void updateAutoPayment() {
        // 자동 결제 설정/변경 로직
    }

    @Operation(summary = "구독 연장", description = "구독 기간을 연장하고 계약을 갱신합니다.")
    @PostMapping("/{detailIdx}/extend")
    public BaseResponse extendSubscription(@PathVariable Long detailIdx, @RequestBody PostExtendRequest dto /*, @AuthenticationPrincipal User user*/ ) {

        User user = User.builder().id("1").build();
        subscribeFacade.extendSubscription(detailIdx, dto , user);
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "구독 정보 조회", description = "현재 내가 구독하고있는 목록을 확인합니다.")
    @GetMapping("/info")
    public BaseResponse<Page<GetSubscribeListRes>> getSubscriptionInfo(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "5") int size) {



        // Todo 삭제할것
        User user = User.builder().id("1").build();

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("idx").descending()
        );


        Page<GetSubscribeListRes> rs = subscribeFacade.getSubscriptionInfo(user, pageable);
        return BaseResponse.onSuccess(rs);

    }

    @Operation(summary = "구독 디테일 조회", description = "현재 구독 중인 상품의 정보를 확인합니다.")
    @GetMapping("/info/{subscribeDetailIdx}")
    public BaseResponse<GetSubscribeDetailInfoRes> getSubscribeDetailInfoRes(@PathVariable Long subscribeDetailIdx/*, @AuthenticationPrincipal User user*/) {

        // Todo 삭제할것
        User user = User.builder().id("1").build();



        GetSubscribeDetailInfoRes rs =subscribeFacade.getReturnDelivery(user,subscribeDetailIdx);
        return BaseResponse.onSuccess(rs);
    }



}
