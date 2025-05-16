package com.example.monthlylifebackend.chatV2.core;

import com.example.monthlylifebackend.chatV2.api.model.document.UserContextDocument;
import com.example.monthlylifebackend.chatV2.repository.EsProductSearchRepository;
import com.example.monthlylifebackend.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;


@Component
@RequiredArgsConstructor
public class EsUserContextManager {

    private final EsProductSearchRepository esProductSearchRepository;

    // 대화 로그 조회
    public List<String> getConversationLog(String userId) {
        return getOrCreate(userId).getConversationLog();
    }

    // 대화 로그 추가
    public void addMessageToConversationLog(String userId, String message) {
        UserContextDocument doc = getOrCreate(userId);
        List<String> log = doc.getConversationLog();
        if (log == null) log = new ArrayList<>();
        log.add(message);
        doc.setConversationLog(log);
        doc.setCreatedAt(Instant.now());
        esProductSearchRepository.save(doc);
    }

    // 검색된 상품 정보 요약 저장 (선택적 기능)
    public void updateLastSearchedItems(String userId, List<Product> products) {
        UserContextDocument doc = getOrCreate(userId);
        List<String> productNames = products.stream()
                .map(Product::getName)
                .toList();

        doc.getConversationLog().add("최근 검색한 상품: " + String.join(", ", productNames));
        doc.setCreatedAt(Instant.now());
        esProductSearchRepository.save(doc);
    }

    // 존재하지 않으면 새로 생성
    private UserContextDocument getOrCreate(String userId) {
        return esProductSearchRepository.findById(userId)
                .orElseGet(() -> {
                    UserContextDocument doc = new UserContextDocument();
                    doc.setUserId(userId);
                    doc.setConversationLog(new ArrayList<>());
                    doc.setCreatedAt(Instant.now());
                    return esProductSearchRepository.save(doc);
                });
    }




}
