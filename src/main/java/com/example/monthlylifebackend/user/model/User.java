package com.example.monthlylifebackend.user.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.subscribe.model.Cart;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User extends BaseEntity   {
    @Id
    @Column(length = 20, nullable = false)
    private String id;

    @Column(length = 20, nullable = false)
    private String phoneNumber;

    @Column(length = 20, nullable = false)
    private String nickName;

    @Column(length = 256)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String eamil;

    private boolean isDelayed;


    @Column(length = 100)
    private String address1;

    @Column(length = 100)
    private String address2;
    
    private LocalDate birth;

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserHasUserTag> userHasUserTagList = new ArrayList<>();


































}