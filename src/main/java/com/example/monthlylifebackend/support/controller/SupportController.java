package com.example.monthlylifebackend.support.controller;

import com.example.monthlylifebackend.support.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/support")
@RequiredArgsConstructor
@Tag(name = "고객지원", description = "FAQ, 공지사항, 수리/분실 신고 등 고객지원 관련 API")
public class SupportController {

    private final SupportService supportService;

    // ===== 관리자 기능 =====

    @Operation(summary = "FAQ 등록", description = "FAQ를 등록합니다. (관리자 전용)")
    @PostMapping("/faq/register")
    public void registerFAQ() {
        // FAQ 등록 로직 구현
    }

    @Operation(summary = "공지사항 등록", description = "공지사항을 등록합니다. (관리자 전용)")
    @PostMapping("/notice/register")
    public void registerNotice() {
        // 공지사항 등록 로직 구현
    }

    @Operation(summary = "카테고리 관리", description = "카테고리를 추가, 수정, 삭제합니다. (관리자 전용)")
    @PostMapping("/category/manage")
    public void manageCategory() {
        // 카테고리 관리 로직 구현
    }

    // ===== 사용자 기능 =====

    @Operation(summary = "FAQ 조회", description = "FAQ 목록을 조회합니다.")
    @GetMapping("/faq")
    public void getFAQ() {
        // FAQ 조회 로직 구현
    }

    @Operation(summary = "수리/분실 신고", description = "제품 수리 또는 분실을 신고합니다.")
    @PostMapping("/repair")
    public void submitRepairRequest() {
        // 수리/분실 신고 로직 구현
    }

    @Operation(summary = "공지사항 조회", description = "공지사항 목록 및 상세 정보를 조회합니다.")
    @GetMapping("/notice")
    public void getNotices() {
        // 공지사항 조회 로직 구현
    }
}
