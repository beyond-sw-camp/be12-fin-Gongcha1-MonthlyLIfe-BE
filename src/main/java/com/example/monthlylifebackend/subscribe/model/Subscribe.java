package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.payment.model.Payment;
import com.example.monthlylifebackend.user.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Schema(description = "구독 엔티티")
public class Subscribe extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "구독 ID", example = "1")
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Schema(description = "구독한 사용자" ,example = "1")
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "billing_key_idx")
    @Schema(description = "빌링 키 정보")
    private BillingKey billingKey;

    @Builder.Default
    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL)
    @Schema(description = "구독 상세 목록")
    private List<SubscribeDetail> subscribeDetailList = new ArrayList<>();

    @Version
    @Schema(description = "버전 (낙관적 락)", example = "0")
    private Long version;



}

