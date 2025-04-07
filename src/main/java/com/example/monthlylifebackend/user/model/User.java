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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private String id;

    private String phoneNumber;

    private String nickName;

    private String password;

    private String name;

    private String eamil;

    private Enum isDelayed;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String address1;

    private LocalDate birth;

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribeList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<UserHasUserTag> userHasUserTagList = new ArrayList<>();


































}