package com.example.monthlylifebackend.user.service;


import com.example.monthlylifebackend.user.dto.req.PostSignupReq;
import com.example.monthlylifebackend.user.mapper.UserMapper;
import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
 
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    public boolean signup(PostSignupReq dto) {
        userRepository.save(userMapper.toEntity(dto));
        return true;
    }
}
