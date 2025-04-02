package com.example.monthlylifebackend.support.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RepairImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idx;

    private String url;

    @ManyToOne
    @JoinColumn(name = "repairRecord_idx")
    private RepairRecord repairRecord;
}
