package com.example.monthlylifebackend.user.mapper;

import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import com.example.monthlylifebackend.user.dto.res.GetUserDetailRes;
import com.example.monthlylifebackend.user.model.Role;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", imports = Role.class)
public interface UserMapper {

    @Mapping(target = "role", expression = "java(Role.ROLE_USER)")
    User toEntity(PostSignupReq dto);

    GetUserDetailRes toGetUserDetailRes(User user);


    @Mapping(target = "overdue", expression = "java(checkOverdue(user))")
    @Mapping(target = "tags", expression = "java(getMockTags())")
    @Mapping(target = "isDelayed", expression = "java(checkIsDelayed(user))")
    @Mapping(target = "subscriptionCount", expression = "java(getSubscriptionCount(user))")
    @Mapping(target = "joined", source = "createdAt")
    GetAdminUserRes toUserInfoRes(User user);

    default String checkOverdue(User user) {
        return user.getSubscribeList().stream()
                .flatMap(sub -> sub.getSubscribeDetailList().stream())
                .anyMatch(detail -> {
                    LocalDate now = LocalDate.now();
                    return !detail.getStartAt().isAfter(now.atStartOfDay()) && !detail.getEndAt().isBefore(now.atStartOfDay());
                }) ? "Y" : "N";
    }

    default Long getSubscriptionCount(User user) {
        return (Long) user.getSubscribeList().stream()
                .flatMap(sub -> sub.getSubscribeDetailList().stream())
                .count();
    }


    default String checkIsDelayed(User user) {
        return Optional.ofNullable(user.getSubscribeList())
                .orElse(List.of())
                .stream()
                .anyMatch(Subscribe::isDelayed) ? "Y" : "N";
    }


    default List<String> getMockTags() {
        return List.of("1인가구", "대학생"); // 예시로 Mock 값
    }
}
