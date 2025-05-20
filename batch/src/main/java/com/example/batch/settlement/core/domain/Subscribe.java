package com.example.batch.settlement.core.domain;

import com.example.batch.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscribe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private boolean isDelayed;



    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long billingKeyIdx;

    @Builder.Default
    @OneToMany(mappedBy = "subscribe")
    @BatchSize(size = 50)
    private List<SubscribeDetail> subscribeDetailList = new ArrayList<>();

    @Version
    private Long version;
}
