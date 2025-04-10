package com.example.monthlylifebackend.user.service;


import com.example.monthlylifebackend.common.exception.handler.UserHandler;
import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.mapper.UserMapper;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.monthlylifebackend.common.code.status.ErrorStatus._DUPLICATED_USER;

@Service
@RequiredArgsConstructor
 
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public boolean signup(PostSignupReq dto) {
        Optional<User> check = userRepository.findById(dto.getId());
        if(check.isPresent()) throw new UserHandler(_DUPLICATED_USER);

        userRepository.save(userMapper.toEntity(dto));
        return true;
    }
}
