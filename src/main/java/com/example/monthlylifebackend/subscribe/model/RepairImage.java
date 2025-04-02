package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class RepairImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String Url;

    @ManyToOne
    @JoinColumn(name = "repairRequest_idx")
    private RepairRequest repairRequest;
}
