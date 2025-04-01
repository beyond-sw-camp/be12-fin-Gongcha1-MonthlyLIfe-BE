package com.example.monthlylifebackend.admin.controller;


import com.example.monthlylifebackend.admin.service.AdminService;
import com.example.monthlylifebackend.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
 

public class AdminController {


    private final AdminService adminService;


    //todo 전체 구독 조회 (주문 관리 및 결제, 연체 관리) 우선순위 1
    @GetMapping("/subscriptions")
    public void getAllSubscriptions() {
        // 전체 구독 조회 및 결제/연체 관리 로직 구현
    }

    //todo 예약 스케줄 조회 (반납/배송 예약 확인) 우선순위 2
    @GetMapping("/schedule")
    public void getScheduleList() {
        // 예약 스케줄 조회 로직 구현
    }

    //todo 예약 스케줄 업데이트 (반납/배송 예약 관리) 우선순위 2
    @PostMapping("/schedule")
    public void updateSchedule() {
        // 예약 스케줄 업데이트 로직 구현
    }

    //todo 통계 리포트 (월간/분기별 데이터 집계 및 시각화) 우선순위 5
    @GetMapping("/report")
    public void getStatisticsReport() {
        // 통계 데이터 집계 및 시각화 로직 구현
    }

    //todo 사용자 목록 조회 (회원 정보, 렌탈 기록, 연체 내역 등) 우선순위 3
    @GetMapping("/users")
    public void getUserList() {
        // 사용자 정보, 렌탈 기록, 연체 내역 조회 로직 구현
    }

    //todo 사용자 정보 업데이트 (회원 관리) 우선순위 3
    @PostMapping("/users")
    public void updateUser() {
        // 사용자 정보 업데이트 로직 구현
    }

    //todo 수리신청/분실 신고 목록 조회 우선순위 4
    @GetMapping("/repair-requests")
    public void getRepairRequests() {
        // 수리신청 및 분실 신고 목록 조회 로직 구현
    }

    //todo 수리신청/분실 신고 상태 업데이트 우선순위 4
    @PostMapping("/repair-requests")
    public void updateRepairRequest() {
        // 수리신청 및 분실 신고 처리 로직 구현
    }

    //todo 정비 이력 등록 우선순위 4
    @PostMapping("/maintenance")
    public void registerMaintenanceHistory() {
        // 정비 이력 등록 로직 구현
    }



}
