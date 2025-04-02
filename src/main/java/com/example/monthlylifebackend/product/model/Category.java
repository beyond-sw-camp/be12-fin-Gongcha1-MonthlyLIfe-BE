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
@Schema(description = "구독 분류 정보")
public class Category extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "구독 분류의 고유값", example ="38")
    private Long idx;  // 카테고리 고유 ID

    @Schema(description = "구독 상품 명", example = "전자레인지")
    @Column(nullable = false, length = 100)
    private String name;  // 카테고리 이름

    @Schema(description = "상위 분류의 고유값")
    @ManyToOne
    @JoinColumn(name = "parent_idx", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Category parent;  // 부모 카테고리

}

