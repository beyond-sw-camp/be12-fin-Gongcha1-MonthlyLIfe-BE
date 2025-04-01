package com.example.monthlylifebackend.subscribe.controller;


import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class SubscribeController {


    private final UserService userService;






}
