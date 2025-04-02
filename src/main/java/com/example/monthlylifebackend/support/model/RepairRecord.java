package com.example.monthlylifebackend.support.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Condition;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RepairRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long itemIdx;

    private LocalDateTime repairedAt;

    private String repairDescription;

    private Long beforeConditionIdx;

    private Long afterConditionIdx;

    @OneToMany(mappedBy = "repairRecord")
    private List<RepairImage> repairImages;

    @ManyToOne
    @JoinColumn(name = "condition_idx")
    private Condition condition;
}
