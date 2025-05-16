package com.example.monthlylifebackend.chatV2.api.model.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@Document(indexName = "user_context")
public class UserContextDocument {

    @Id
    private String userId; // ES에서는 보통 이걸 id로 사용

    private List<String> conversationLog;  // 대화 히스토리

    @Field(type = FieldType.Date)
    private Instant createdAt;
}
