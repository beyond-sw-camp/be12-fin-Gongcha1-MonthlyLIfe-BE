package com.example.monthlylifebackend.payment.service;

import com.example.monthlylifebackend.common.exception.handler.PaymentHandler;
import com.example.monthlylifebackend.payment.CustomPaymentClient;
import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.res.GetPaymentMethodRes;
import com.example.monthlylifebackend.payment.mapper.BillingKeyMapper;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.payment.repository.BillingKeyRepository;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.utils.AesUtil;
import io.portone.sdk.server.common.Bank;
import io.portone.sdk.server.common.Card;
import io.portone.sdk.server.payment.billingkey.BillingKeyInfo;
import io.portone.sdk.server.payment.billingkey.BillingKeyPaymentMethod;
import io.portone.sdk.server.payment.billingkey.BillingKeyPaymentMethodCard;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.Objects;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._UNSUPPORTED_PAYMENT_METHOD;

@Service
@RequiredArgsConstructor
public class BillingKeyService {
    private final BillingKeyRepository billingKeyRepository;

    private final CustomPaymentClient customPaymentClient;
    private final BillingKeyMapper billingKeyMapper;
    private final AesUtil aesUtil;

    //빌링키를 포트원에 확인하고 저장하는 로직
    public Long createPaymentMethod(PostBillingKeyReq dto, User user) {
        //빌링키를 포트원에 확인
        String rawKey = dto.getBillingKey();
        BillingKey billingKey = checkBillingKey(rawKey, user);

        //빌링키 암호화 하여 저장
        billingKey.setBillingKey(aesUtil.encrypt(billingKey.getBillingKey()));

        //카드번호 마스킹하기
        billingKey.setCardNumber(maskCard(billingKey.getCardNumber()));

        BillingKey result = billingKeyRepository.save(billingKey);
        return result.getIdx();
    }

    // 디코딩된 빌링키가 필요할 때
    public String getBillingKey(Long idx) {
        BillingKey entity = billingKeyRepository.findById(idx).orElseThrow();

        String billingKey = aesUtil.decrypt(entity.getBillingKey());
        return billingKey;
    }

    //
    public Page<GetPaymentMethodRes> getPaymentMethodByUser(User user, int page, int size) {
        Page<BillingKey> billingKeyPage = billingKeyRepository.findAllByUser(user, PageRequest.of(page, size));
        return billingKeyMapper.toDtoPage(billingKeyPage);
    }


    //포트원에 빌링키를 체크해서 결제 수단 정보를 저장
    private BillingKey checkBillingKey(String billingKey, User user) {
        BillingKeyInfo info = customPaymentClient.checkBillingKey(billingKey);

        if (!(info instanceof BillingKeyInfo.Recognized recognized))
            throw new PaymentHandler(_UNSUPPORTED_PAYMENT_METHOD);
        //카드만 받도록 아니면 예외
        if(Objects.requireNonNull(recognized.getMethods()).isEmpty())
            throw new PaymentHandler(_UNSUPPORTED_PAYMENT_METHOD);
        BillingKeyPaymentMethod method = recognized.getMethods().get(0);
        if(!(method instanceof BillingKeyPaymentMethodCard))
            throw new PaymentHandler(_UNSUPPORTED_PAYMENT_METHOD);
        Card card = ((BillingKeyPaymentMethodCard) method).getCard();

        return BillingKey.builder()
                .billingKey(billingKey)
                .cardCompany(card.getName())
                .cardNumber(card.getNumber())
                .user(user)
                .build();
    }

    // 카드 번호 마스킹하기
    private String maskCard(String input) {
        if (input == null || input.length() < 8)
            throw new PaymentHandler(_UNSUPPORTED_PAYMENT_METHOD);

        String masked = "*".repeat(8);
        return masked + input.substring(8);
    }
}
