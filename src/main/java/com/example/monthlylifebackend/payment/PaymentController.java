package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.payment.dto.res.GetPaymentMethodRes;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
@Tag(name = "Payment", description = "결제 관련 API")
public class PaymentController {

    private final PaymentFacade paymentFacade;

    @Operation(summary = "결제 테스트", description = "결제 테스트를 위해 빌링 키를 사용해 결제를 시작합니다.")
    @PostMapping("/billing")
    public BaseResponse<String> update(@RequestBody PostBillingKeyReq dto,
                                       @AuthenticationPrincipal @Valid @NotNull User user) {
        paymentFacade.startPayment(dto, user);
        return BaseResponse.onSuccess("success");
    }

    @Operation(summary = "결제 수단 등록", description = "빌링 키를 이용해서 고객의 결제 수단을 등록합니다.")
    @PostMapping("/method")
    public BaseResponse<Long> createMethod(@RequestBody PostBillingKeyReq dto,
                                           @AuthenticationPrincipal @Valid @NotNull User user) {
        Long idx = paymentFacade.createPaymentMethod(dto, user);
        return BaseResponse.onSuccess(idx);
    }

    @Operation(summary = "결제 수단 확인", description = "유저 토큰을 이용해서 고객의 결제 수단을 확인합니다.")
    @GetMapping("/method")
    public BaseResponse<Page<GetPaymentMethodRes>> getMethod(@AuthenticationPrincipal @Valid @NotNull User user,
                                                             @RequestParam int page,
                                                             @RequestParam int size) {
        Page<GetPaymentMethodRes> getPaymentMethodResPage = paymentFacade.getPaymentMethodResPage(user, page, size);
        return BaseResponse.onSuccess(getPaymentMethodResPage);
    }

    @Operation(summary = "정기 결제 웹훅 처리", description = """
        포트원에서 결제 완료 웹훅을 받으면 이를 검증하고,
        다음달에도 결제가 추가로 필요하면 이를 예약해서 정기 결제를 구현합니다.
        """)
    @PostMapping("/webhook")
    public void webhook(@RequestBody PostWebhookReq dto) {
        paymentFacade.getWebhook(dto);
    }

    @Operation(summary = "빌링 키 유효성 검사", description = "주어진 빌링 키가 유효한지 확인합니다.")
    @PostMapping("/check")
    public void check(@RequestBody PostBillingKeyReq dto) {
        paymentFacade.check(dto.getBillingKey());
    }
}
