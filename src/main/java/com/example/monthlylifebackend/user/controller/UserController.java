package com.example.monthlylifebackend.user.controller;


import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class UserController {


    private final UserService userService;


    //Todo 회원가입 우선순위 1
    @PostMapping("/")
    public void 회원가입(){

    }
    //Todo 아이피/비밀번호 찾기 회원가입 우선순위 3
    @PostMapping("/")
    public void 아이피_비밀번호_찾기(){

    }//Todo 비밀번호 변경 회원가입 우선순위 3
    @PostMapping("/")
    public void 비번_변경(){

    }//Todo 회원 탈퇴 우선순위 3
    @GetMapping("/")
    public void 회원탈퇴(){

    }//Todo 회원 정보 수정 우선순위 2
    @PostMapping("/")
    public void 회원정보수정(){

    }

    //Todo 회원 상세 페이지 우선순위 3
    @GetMapping("/")
    public void 회원_상세_페이지(){

    }






}
