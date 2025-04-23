package com.example.monthlylifebackend.user.mapper;

import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(PostSignupReq dto);
}
