package com.example.monthlylifebackend.admin.controller;

import com.example.monthlylifebackend.admin.dto.request.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.response.GetProductItemRes;
import com.example.monthlylifebackend.admin.dto.response.GetProductRes;
import com.example.monthlylifebackend.admin.facade.AdminFacade;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.subscribe.model.ReturnLocation;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Tag(name = "어드민 api")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    @Operation(summary = "관리자 전체 페이징 재고 조회", description = "판매 아이템의 재고 관리페이지를 페이징 처리하여 조회합니다.")
    @GetMapping("/product-by-page")
    public BaseResponse<Page<GetProductRes>> getAllItemsByPage(        @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(required = false) String productName,
                                                                       @RequestParam(required = false) String manufacturer,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        Page<GetProductRes> dto = adminFacade.findAllItemsByPage(page, size, productName, manufacturer, startDate, endDate);
        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "관리자 전체 재고 조회", description = "판매 아이템의 재고를 관리페이지를 조회합니다.")
    @GetMapping("/product")
    public BaseResponse<List<GetProductRes>> getAllItemsByPage() {
        List<GetProductRes> dto = adminFacade.findAllItems();
        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "관리자 상세 재고 관리", description = "판매 아이템의 재고 상세 페이지를 조회합니다.")
    @GetMapping("/item-detail/{idx}")
    public BaseResponse<GetProductItemRes> getAllItemDetails(@PathVariable String idx) {
        GetProductItemRes dto = adminFacade.findItemDetail(idx);
        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "관리자 재고 변경", description = "판매 아이템의 재고를 관리페이지를 변경합니다.")
    @PatchMapping("/item-detail/{idx}")
    public BaseResponse changeItemCount(@PathVariable String idx,
                                        @RequestBody PatchItemCountReq req) {
        adminFacade.modifyItemCount(req);
        return BaseResponse.onSuccess(null);
    }

    @Operation(summary = "배송 스케줄 관리 조회", description = "페이지 별로 반납 및 배송 예약 일정을 조회합니다.")
    @GetMapping("/delivery-by-page")
    public BaseResponse getDeliveryScheduleListByPage(    @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(required = false) String searchType,
                                                          @RequestParam(required = false) String searchQuery,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        Page<GetDeliveryListRes> dto = adminFacade.findAllDeliveryByPage(page, size, searchType, searchQuery, dateFrom, dateTo);
        return BaseResponse.onSuccess(dto);

    }

    @Operation(summary = "결제 및 연체 관리", description = "결제의 연체 여부와 사용자와 결제 정보를 조회합니다.")
    @GetMapping("/payments")
    public BaseResponse getPaymentListByPage(    @RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size,
                                                 @RequestParam(required = false) String searchType,
                                                 @RequestParam(required = false) String searchQuery,
                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                 @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                 @RequestParam(defaultValue = "false") boolean overdueOnly) {


        return BaseResponse.onSuccess(adminFacade.getPayments(page, size, searchType,searchQuery, dateFrom, dateTo, overdueOnly));
    }

    @Operation(summary = "예약 스케줄 업데이트", description = "반납/배송 스케줄을 수정합니다.")
    @PostMapping("/schedule")
    public void updateSchedule() {
        // 로직
    }


    @Operation(summary = "전체 구독 조회", description = "주문, 결제, 연체 관리용 전체 구독 내역을 조회합니다.")
    @GetMapping("/subscribe")
    public BaseResponse<Page<GetAdminSubscribeRes>> getAllSubscriptionsByPage(    @RequestParam int page,
                                                                                  @RequestParam int size,
                                                                                  @RequestParam(required = false) String keyword,
                                                                                  @RequestParam(required = false) String status,
                                                                                  @RequestParam(required = false) Integer minMonths,
                                                                                  @RequestParam(required = false) Integer maxMonths) {
        // 로직
        return BaseResponse.onSuccess(adminFacade.getSubscibeByPage(page, size, keyword, status, minMonths, maxMonths));
    }

    @Operation(summary = "구독 상세 조회", description = "주문, 결제, 연체 관리용 전체 구독 상세 내역을 조회합니다.")
    @GetMapping("/subscribe-detail/{subscribeId}")
    public BaseResponse<List<GetAdminSubscribeDetailRes>> getAllSubscriptionsDetailByPage(@PathVariable Long subscribeId) {
        return BaseResponse.onSuccess(adminFacade.getAdminSubscribeDetail(subscribeId));

    }



    @Operation(summary = "통계 리포트 조회", description = "월간 및 분기별 통계 데이터를 조회합니다.")
    @GetMapping("/report")
    public void getStatisticsReport() {
        // 로직
    }

    @Operation(summary = "사용자 목록 조회", description = "전체 사용자, 렌탈 기록 및 연체 내역을 확인합니다.")
    @GetMapping("/users")
    public BaseResponse<Page<GetAdminUserRes>> getUsers(    @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size,
                                                            @RequestParam(required = false) String sort,
                                                            @RequestParam(required = false) String searchType,
                                                            @RequestParam(required = false) String searchQuery,
                                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                            @RequestParam(required = false) String tags, // "1인사무실,2인가구"
                                                            @RequestParam(defaultValue = "false") boolean overdueOnly) {
        Page<GetAdminUserRes> users = adminFacade.getAdminUserList(page, size, sort, searchType, searchQuery, dateFrom, dateTo,
                //tags,
                overdueOnly);
        return BaseResponse.onSuccess(users);
    }


    @Operation(summary = "사용자 정보 업데이트", description = "관리자용 사용자 정보 수정 기능입니다.")
    @PostMapping("/users")
    public void updateUser() {
        // 로직
    }



    @Operation(summary = "관리자 반납 요청 조회", description = "반납 및 수리 요청 목록을 조회합니다.")
    @GetMapping("/return-request")
        public BaseResponse<Page<GetAdminReturnDeliveryRes>> getReturnRequestList(
        @RequestParam int page,
        @RequestParam int size,
        @RequestParam(required = false) ReturnDeliveryStatus status,
        @RequestParam(required = false) LocalDate dateFrom,
        @RequestParam(required = false) LocalDate dateTo
    ) {
            return BaseResponse.onSuccess(adminFacade.getReturnRequestList(PageRequest.of(page, size), status, dateFrom, dateTo));
        }

    @Operation(summary = "관리자 수리 요청 조회", description = "반납 및 수리 요청 목록을 조회합니다.")
    @GetMapping("/repair-request")
    public BaseResponse<Page<GetAdminReturnDeliveryRes>> getRepairRequestList(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(required = false) ReturnDeliveryStatus status,
            @RequestParam(required = false) LocalDate dateFrom,
            @RequestParam(required = false) LocalDate dateTo
    ) {
        return BaseResponse.onSuccess(adminFacade.getRepairRequestList(PageRequest.of(page, size), status, dateFrom, dateTo));
    }





    @Operation(summary = "관리자 반납 상태 업데이트 (수락/거절)", description = "관리자가 반납 요청의 상태를 업데이트 합니다.")
    @PostMapping("/return/{returnDeliveryIdx}")
    public BaseResponse updateReturnRequest(@PathVariable Long returnDeliveryIdx,
                                            @RequestBody PostAdminReturnDeliveryReq dto) {
        adminFacade.updateReturnRequest(returnDeliveryIdx, dto);
        return BaseResponse.onSuccess(null);

    }

    @Operation(summary = "관리자 수리 상태 업데이트 (수락/거절)", description = "관리자가 반납 요청의 상태를 업데이트 합니다.")
    @PostMapping("/repair/{returnDeliveryIdx}")
    public BaseResponse updateRepairRequest(@PathVariable Long returnDeliveryIdx,
                                            @RequestBody PostAdminReturnDeliveryReq dto) {
        adminFacade.updateRepairRequest(returnDeliveryIdx,dto);

        return BaseResponse.onSuccess(null);
    }




    @Operation(summary = "배송 스케줄 관리 조회", description = "반납 및 배송 예약 일정을 조회합니다.")
    @GetMapping("/delivery")
    public BaseResponse getDeliveryScheduleList() {
        List<GetDeliveryListRes> dto = adminFacade.findAllDelivery();
        return BaseResponse.onSuccess(dto);

    }

}