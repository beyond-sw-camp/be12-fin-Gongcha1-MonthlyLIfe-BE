package com.example.monthlylifebackend.support.controller;


import com.example.monthlylifebackend.support.service.SupportService;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Log4j2

public class SupportController {


    private final SupportService supportService;


    // ===== 홈페이지 운영 관리 기능 =====
    // todo FAQ 등록 (관리자) - 우선순위 3
    // POST /faq/register
    @PostMapping("/")
    public void registerFAQ() {
        // FAQ 등록 로직 구현
    }

    // todo 공지사항 등록 (관리자) - 우선순위 3
    // POST /notice/register
    @PostMapping(" ")
    public void registerNotice() {
        // 공지사항 등록 로직 구현
    }

    // todo 카테고리 관리 (관리자) - 우선순위 5
    // POST /category/manage
    @PostMapping(" ")
    public void manageCategory() {
        // 카테고리 추가, 수정, 삭제 등의 로직 구현
    }



    // todo FAQ 조회 - 우선순위 5
    // GET /faq
    @GetMapping(" ")
    public void getFAQ() {
        // FAQ 조회 로직 구현
    }

    // todo 수리신청/분실신고 - 우선순위 4
    // POST /repair
    @PostMapping(" ")
    public void submitRepairRequest() {
        // 수리신청 및 분실신고 등록 로직 구현
    }

    // todo 공지사항 목록 및 상세 조회 - 우선순위 5
    // GET /notice
    @GetMapping(" ")
    public void getNotices() {
        // 공지사항 목록 및 상세 조회 로직 구현
    }




}
