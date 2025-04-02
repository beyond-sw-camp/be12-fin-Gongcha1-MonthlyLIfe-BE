package com.example.monthlylifebackend.user.model;

import com.example.monthlylifebackend.common.BaseEntity;
import com.example.monthlylifebackend.product.model.SaleHasUserTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @OneToMany(mappedBy = "user_tag")
    private List<SaleHasUserTag> saleHasUserTagList = new ArrayList<>();

    @OneToMany(mappedBy = "user_tag")
    private List<UserHasUserTag> userHasUserTagList = new ArrayList<>();



}
