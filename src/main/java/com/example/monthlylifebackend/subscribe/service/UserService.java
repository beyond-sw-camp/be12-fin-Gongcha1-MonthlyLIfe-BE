package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {



    private final UserRepository userRepository;
}
