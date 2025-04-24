package com.example.monthlylifebackend.user.facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.dto.res.GetUserDetailRes;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    public String signup(PostSignupReq dto) {
        return userService.signup(dto);
    }

    public GetUserDetailRes getUserDetail(User user) {return userService.getUserDetail(user); }

    public boolean deleteUser(User user) { return userService.deleteUser(user); }
}
