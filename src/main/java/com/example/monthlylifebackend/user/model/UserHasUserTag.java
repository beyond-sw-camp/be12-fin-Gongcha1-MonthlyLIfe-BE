package com.example.monthlylifebackend.user.model;

import com.example.monthlylifebackend.common.BaseEntity;
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
public class UserHasUserTag extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @ManyToOne
    @JoinColumn(name = "userTag_idx")
    private UserTag userTag;

    @ManyToOne
    @JoinColumn(name = "condition_idx")
    private User user;
}