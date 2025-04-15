package com.example.monthlylifebackend.payment;

import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.common.example.ExampleEntity;
import com.example.monthlylifebackend.exam.ExamService;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookDataReq;
import com.example.monthlylifebackend.payment.dto.req.PostWebhookReq;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("payment")
public class PaymentController {


    private final PaymentFacade paymentFacade;

    @PostMapping("/billing")
    public ResponseEntity<BaseResponse<String>> update(@RequestBody PostBillingKeyReq dto) {
        System.out.println(dto.getBillingKey());
        paymentFacade.startPayment(dto);
        return ResponseEntity.ok(BaseResponse.onSuccess("success"));
    }

    @PostMapping("/webhook")
    public void webhook(@RequestBody PostWebhookReq dto) {
         System.out.println(dto);
    }
}