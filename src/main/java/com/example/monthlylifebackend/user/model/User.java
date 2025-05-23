package com.example.monthlylifebackend.user.model;

import com.example.monthlylifebackend.cart.model.Cart;
import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.ArrayList;
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

    @Column(length = 256)
    private String password;

    @Column(length = 10, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @ColumnDefault("false")
    private boolean isDelayed;

    @Column(length = 10)
    private String postalCode;

    @Column(length = 100)
    private String address1;

    @Column(length = 100)
    private String address2;

    private LocalDate birth;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Subscribe> subscribeList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Cart> cartList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<UserHasUserTag> userHasUserTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<BillingKey> billingKeyList = new ArrayList<>();

    public void setPassword(String password) {
        this.password = password;
    }

    public void setDelayed(boolean isDelayed) { this.isDelayed = isDelayed; }
}