package com.example.monthlylifebackend.chatV2.service;

import com.example.monthlylifebackend.chatV2.api.model.document.UserContextDocument;
import com.example.monthlylifebackend.chatV2.repository.EsProductSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EsChatLogService {

    private final EsProductSearchRepository esRepo;

    @Async
    public void saveUserLogAsync(String userId, String message) {
        // 기존 로직 복붙
        UserContextDocument doc = esRepo.findById(userId).orElse(null);

        if (doc == null) {
            // 유저가 처음 왔을 때 → 메시지 포함해서 초기화
            doc = new UserContextDocument();
            doc.setUserId(userId);
            List<String> initLog = new ArrayList<>();
            initLog.add(message);
            doc.setConversationLog(initLog);
            doc.setCreatedAt(Instant.now());
        } else {
            // 기존 유저 → 그냥 append
            doc.getConversationLog().add(message);
        }

        doc.setCreatedAt(Instant.now());
        esRepo.save(doc);
    }


}
