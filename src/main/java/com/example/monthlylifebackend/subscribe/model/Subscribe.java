package com.example.monthlylifebackend.subscribe.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.user.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    //주문 시 총 가격
    private int price;

    private int period;



    @ManyToOne
    @JoinColumn(name = "user_idx")
    private User user;


    @ManyToOne(cascade = CascadeType.PERSIST)  // CascadeType.PERSIST 설정
    @JoinColumn(name = "payment_idx")
    private Payment payment;


    @Builder.Default
    @OneToMany(mappedBy = "subscribe", cascade = CascadeType.ALL)  // CascadeType.ALL로 설정
    private List<SubscribeDetail> subscribeDetailList = new ArrayList<>();

    @Version
    private Long version;



}

