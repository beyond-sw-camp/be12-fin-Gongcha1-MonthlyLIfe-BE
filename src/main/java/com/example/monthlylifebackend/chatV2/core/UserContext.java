package com.example.monthlylifebackend.chatV2.core;


import com.example.monthlylifebackend.product.model.Product;
import lombok.Data;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Setter
@Data
public class UserContext {
    private String userId;
    private List<String> conversationLog = new ArrayList<>();  // 기본값으로 빈 리스트를 설정
     private List<String> subscribedItems;
    private List<Product> lastSearchedItem;
    private LocalDateTime lastInteractionTime;
}