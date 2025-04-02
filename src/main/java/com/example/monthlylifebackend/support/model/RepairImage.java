package com.example.monthlylifebackend.support.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "상품 수리 이미지")
public class RepairImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    @Column(length = 45)
    private String url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repair_record_idx")
    private RepairRecord repairRecord;
}
