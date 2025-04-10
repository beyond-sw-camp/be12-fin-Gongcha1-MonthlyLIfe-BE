package com.example.monthlylifebackend.admin.controller;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetItemListRes;
import com.example.monthlylifebackend.admin.facade.AdminFacade;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "어드민 api")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    @Operation(summary = "관리자 전체 재고 관리", description = "판매 아이템의 재고를 관리페이지를 조회합니다.")
    @GetMapping("/item")
    public BaseResponse<Page<GetItemListRes>> getAllItems(int page, int size) {
        Page<GetItemListRes> dto = adminFacade.findAllItemsByPage(page,size);
        return BaseResponse.onSuccess(dto);
    }
    @Operation(summary = "관리자 재고 변경", description = "판매 아이템의 재고를 관리페이지를 변경합니다.")
    @PatchMapping("/item/{itemIdx}")
    public ResponseEntity getAllItems(@RequestBody PatchItemCountReq req) {
        adminFacade.modifyItemCount(req);
        return ResponseEntity.ok(null);
    }

    @Operation(summary = "배송 스케줄 관리 조회", description = "반납 및 배송 예약 일정을 조회합니다.")
    @GetMapping("/delivery")
    public ResponseEntity getDeliveryScheduleList(int page, int size) {
        Page<GetDeliveryListRes> dto = adminFacade.findAllDeliveryByPage(page,size);
        return ResponseEntity.ok(dto);

    }

    @Operation(summary = "예약 스케줄 업데이트", description = "반납/배송 스케줄을 수정합니다.")
    @PostMapping("/schedule")
    public void updateSchedule() {
        // 로직
    }




    @Operation(summary = "전체 구독 조회", description = "주문, 결제, 연체 관리용 전체 구독 내역을 조회합니다.")
    @GetMapping("/subscriptions")
    public void getAllSubscriptions() {
        // 로직
    }



    @Operation(summary = "통계 리포트 조회", description = "월간 및 분기별 통계 데이터를 조회합니다.")
    @GetMapping("/report")
    public void getStatisticsReport() {
        // 로직
    }

    @Operation(summary = "사용자 목록 조회", description = "전체 사용자, 렌탈 기록 및 연체 내역을 확인합니다.")
    @GetMapping("/users")
    public void getUserList() {
        // 로직
    }

    @Operation(summary = "사용자 정보 업데이트", description = "관리자용 사용자 정보 수정 기능입니다.")
    @PostMapping("/users")
    public void updateUser() {
        // 로직
    }

    @Operation(summary = "수리/분실 요청 조회", description = "수리 요청 및 분실 신고 목록을 조회합니다.")
    @GetMapping("/repair-requests")
    public void getRepairRequests() {
        // 로직
    }

    @Operation(summary = "수리/분실 상태 업데이트", description = "요청된 수리 또는 분실 상태를 갱신합니다.")
    @PostMapping("/repair-requests")
    public void updateRepairRequest() {
        // 로직
    }

    @Operation(summary = "정비 이력 등록", description = "제품 정비 내역을 신규 등록합니다.")
    @PostMapping("/maintenance")
    public void registerMaintenanceHistory() {
        // 로직
    }
}
