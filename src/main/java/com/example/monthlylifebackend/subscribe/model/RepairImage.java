package com.example.monthlylifebackend.subscribe.model;

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
    private Long idx;

    private String url;

    @ManyToOne
    @JoinColumn(name = "repairRequest_idx")
    private RepairRequest repairRequest;
}
