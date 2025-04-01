package com.example.monthlylifebackend.support.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Condition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "상품 수리 기록")
public class RepairRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long itemIdx;

    private LocalDateTime repairedAt;

    @Column(columnDefinition = "TEXT")
    private String repairDescription;

    private Long beforeConditionIdx;

    private Long afterConditionIdx;

    @OneToMany(mappedBy = "repairRecord", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RepairImage> repairImages;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "condition_idx")
    private Condition condition;
}
