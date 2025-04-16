package com.example.monthlylifebackend.payment.service;

import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.mapper.BillingKeyMapper;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.payment.repository.BillingKeyRepository;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.utils.AesUtil;
import io.portone.sdk.server.common.Bank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BillingKeyService {
    private final BillingKeyRepository billingKeyRepository;
    private final BillingKeyMapper billingKeyMapper;
    private final AesUtil aesUtil;

    public Long createBillingKey(PostBillingKeyReq dto, User user) {
        BillingKey billingKey = billingKeyMapper.toEntity(dto, user);

        billingKey.setBillingKey(aesUtil.encrypt(billingKey.getBillingKey()));
        BillingKey result = billingKeyRepository.save(billingKey);
        return result.getIdx();
    }

    public String getBillingKey(Long idx) {
        BillingKey entity = billingKeyRepository.findById(idx).orElseThrow();

        String billingKey = aesUtil.decrypt(entity.getBillingKey());
        return billingKey;
    }
}
