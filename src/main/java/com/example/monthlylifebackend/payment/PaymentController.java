package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {


    private final PaymentFacade paymentFacade;

    @PostMapping("/billing")
    public ResponseEntity<BaseResponse<String>> update(@RequestBody PostBillingKeyReq dto, @AuthenticationPrincipal User user) {
        System.out.println(dto.getBillingKey());
        paymentFacade.startPayment(dto, user);
        return ResponseEntity.ok(BaseResponse.onSuccess("success"));
    }

    @PostMapping("/webhook")
    public void webhook(@RequestBody PostWebhookReq dto) {
         System.out.println(dto);
         paymentFacade.getWebhook(dto);
    }

    @PostMapping("/check")
    public void check(@RequestBody PostBillingKeyReq dto) {
        paymentFacade.check(dto.getBillingKey());
    }
}