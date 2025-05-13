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
        UserContextDocument doc = esRepo.findById(userId)
                .orElseGet(() -> {
                    var newDoc = new UserContextDocument();
                    newDoc.setUserId(userId);
                    newDoc.setConversationLog(new ArrayList<>());
                    newDoc.setCreatedAt(Instant.now());
                    return newDoc;
                });

        List<String> log = doc.getConversationLog();
        log.add(message);
        doc.setCreatedAt(Instant.now());
        esRepo.save(doc);
    }


}
