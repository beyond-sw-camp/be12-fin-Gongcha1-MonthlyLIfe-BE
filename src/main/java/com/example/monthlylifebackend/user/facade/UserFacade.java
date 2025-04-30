package com.example.monthlylifebackend.user.facade;

import com.example.monthlylifebackend.common.customAnnotation.Facade;
import com.example.monthlylifebackend.subscribe.service.SubscribeService;
import com.example.monthlylifebackend.user.dto.req.PostCheckIdReq;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.dto.res.GetUserDetailRes;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Facade
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final SubscribeService subscribeService;
    public String signup(PostSignupReq dto) {
        return userService.signup(dto);
    }

    public GetUserDetailRes getUserDetail(User user) {return userService.getUserDetail(user); }

    @Transactional
    public boolean deleteUser(User user) {
        subscribeService.changeSubscriberNull(user);
        return userService.deleteUser(user);
    }

    public boolean checkId(PostCheckIdReq dto) {return userService.checkId(dto);}
}
