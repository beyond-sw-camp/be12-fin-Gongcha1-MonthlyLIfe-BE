package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.subscribe.repository.RepairRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RepairRequestService {
    private final RepairRequestRepository repairRequestRepository;

    public Long countAll() {
        return repairRequestRepository.count();
    }
}
