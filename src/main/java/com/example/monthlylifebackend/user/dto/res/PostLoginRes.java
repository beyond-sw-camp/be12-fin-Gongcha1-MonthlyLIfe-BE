package com.example.monthlylifebackend.user.dto.res;

import com.example.monthlylifebackend.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostLoginRes {
    private String id;
    private Role role;
    private Long expired;
}
