package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.support.model.RepairImage;
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

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String description;

    private String status;

    @ManyToOne
    @JoinColumn(name = "subscribeDetail_idx")
    private SubscribeDetail subscribeDetail;

    @OneToMany(mappedBy = "repairRequest")
    private List<RepairImage> repairImageList = new ArrayList<>();

}
