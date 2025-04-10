package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.Item;
import com.example.monthlylifebackend.support.model.RepairImage;
import com.example.monthlylifebackend.user.model.User;
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
public class Subscribe extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;





    @ManyToOne
    @JoinColumn(name = "payment_idx")
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;

    @OneToMany(mappedBy = "subscribe")
    private List<SubscribeDetail> subscribeDetailList;



}

