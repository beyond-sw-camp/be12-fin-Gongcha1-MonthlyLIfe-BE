package com.example.monthlylifebackend.payment.service;

import com.example.monthlylifebackend.payment.model.Settlement;
import com.example.monthlylifebackend.payment.repository.SettlementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SettlementService {
    private final SettlementRepository settlementRepository;

    public void save(Settlement settlement) {
        settlementRepository.save(settlement);
    }
}
