package com.example.monthlylifebackend.product.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
// 구독 분류 정보
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  // 카테고리 고유 ID

    private String name;  // 카테고리 이름

    @ManyToOne
    @JoinColumn(name = "parent_idx")
    private Category parent;  // 부모 카테고리

    private String iconUrl; // 카테고리 아이콘

}

