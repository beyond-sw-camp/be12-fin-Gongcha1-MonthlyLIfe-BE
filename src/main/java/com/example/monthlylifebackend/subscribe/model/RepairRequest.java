package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RepairRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String description;


    @Enumerated(EnumType.STRING)
    private ReportType status;

    private String subscriberName;
    private String subscriberPhone;
    @ManyToOne
    @JoinColumn(name = "subscribeDetail_idx")
    private SubscribeDetail subscribeDetail;

    @OneToMany(mappedBy = "repairRequest",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<RepairImage> repairImageList = new ArrayList<>();
}




