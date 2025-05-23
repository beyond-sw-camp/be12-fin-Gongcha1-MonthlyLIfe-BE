package com.example.monthlylifebackend.admin.controller;

import com.example.monthlylifebackend.admin.dto.req.PatchItemCountReq;
import com.example.monthlylifebackend.admin.dto.res.*;
import com.example.monthlylifebackend.admin.facade.AdminFacade;
import com.example.monthlylifebackend.common.BaseResponse;
import com.example.monthlylifebackend.payment.dto.res.GetAdminPaymentRes;
import com.example.monthlylifebackend.payment.dto.res.GetAdminRecentPaymentRes;
import com.example.monthlylifebackend.subscribe.dto.req.PostAdminReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminReturnDeliveryRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetAdminSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.model.ReturnDeliveryStatus;
import com.example.monthlylifebackend.user.dto.res.GetAdminRecentUserRes;
import com.example.monthlylifebackend.user.dto.res.GetAdminUserRes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Tag(name = "관리자")
@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminFacade adminFacade;

    //0. 관리자 대시보드
    @Operation(summary = "관리자 대시보드 카드뷰 조회", description = "총 사용자 수, 이번달 예상 매출액을 조회합니다.")
    @GetMapping("/cardview")
    public BaseResponse<GetAdminHomeCardRes> getCardView() {
        GetAdminHomeCardRes dto = adminFacade.findCardView();
        return BaseResponse.onSuccess(dto);
    }



    //1. 상품 재고 관리
    //1-1) 상품 재고 관리 - 상품 관리
//    @Operation(summary = "관리자 전체 페이징 재고 조회", description = "판매 아이템의 재고 관리페이지를 페이징 처리하여 조회합니다.")
//    @GetMapping("/product-by-page")
//    public BaseResponse<Page<GetProductRes>> getAllItemsByPage(        @RequestParam(defaultValue = "0") int page,
//                                                                       @RequestParam(defaultValue = "10") int size,
//                                                                       @RequestParam(required = false) String productName,
//                                                                       @RequestParam(required = false) String manufacturer,
//                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
//                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
//        Page<GetProductRes> dto = adminFacade.findAllItemsByPage(page, size, productName, manufacturer, startDate, endDate);
//        return BaseResponse.onSuccess(dto);
//    }

    @Operation(summary = "관리자 전체 재고 조회", description = "판매 아이템의 재고를 관리페이지를 조회합니다.")
    @GetMapping("/product")
    public BaseResponse<List<GetProductRes>> getAllItemsByPage() {
        List<GetProductRes> dto = adminFacade.findAllItems();
        return BaseResponse.onSuccess(dto);
    }

    //1-1-1) 상품 재고 관리 - 재고 관리 - 상품 재고 관리 상세
    @Operation(summary = "관리자 상세 재고 조회", description = "판매 아이템의 재고 상세 페이지를 조회합니다.")
    @GetMapping("/item-detail/{idx}")
    public BaseResponse<GetProductItemRes> getAllItemDetails(@PathVariable String idx) {
        GetProductItemRes dto = adminFacade.findItemDetail(idx);
        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "관리자 재고 상세 변경", description = "판매 아이템의 재고를 관리페이지를 변경합니다.")
    @PatchMapping("/item-detail/{idx}")
    public BaseResponse<?> changeItemCount(@PathVariable Long idx,
                                        @RequestBody PatchItemCountReq req) {
        adminFacade.modifyItemCount(idx, req.getCount());
        return BaseResponse.onSuccess(null);
    }


    //1-2) 구독 관리
    @Operation(summary = "전체 구독 조회", description = "주문, 결제, 연체 관리용 전체 구독 내역을 조회합니다.")
    @GetMapping("/subscribe")
    public BaseResponse<Page<GetAdminSubscribeRes>> getAllSubscriptionsByPage(    @RequestParam int page,
                                                                                  @RequestParam int size,
                                                                                  @RequestParam(required = false) String keyword,
                                                                                  @RequestParam(required = false) String status,
                                                                                  @RequestParam(required = false) Integer minMonths,
                                                                                  @RequestParam(required = false) Integer maxMonths) {
        return BaseResponse.onSuccess(adminFacade.getSubscibeByPage(page, size, keyword, status, minMonths, maxMonths));
    }

    @Operation(summary = "구독 상세 조회", description = "주문, 결제, 연체 관리용 전체 구독 상세 내역을 조회합니다.")
    @GetMapping("/subscribe-detail/{subscribeId}")
    public BaseResponse<List<GetAdminSubscribeDetailRes>> getAllSubscriptionsDetailByPage(@PathVariable Long subscribeId) {
        return BaseResponse.onSuccess(adminFacade.getAdminSubscribeDetail(subscribeId));
    }



    //2. 결제 관리
    //2-1) 결제 및 연체 관리
    @Operation(summary = "결제 및 연체 관리", description = "결제의 연체 여부와 사용자와 결제 정보를 조회합니다.")
    @GetMapping("/payments")
    public BaseResponse<Page<GetAdminPaymentRes>> getPaymentListByPage(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "10") int size,
                                                                       @RequestParam(required = false) String searchType,
                                                                       @RequestParam(required = false) String searchQuery,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
                                                                       @RequestParam(defaultValue = "false") boolean overdueOnly) {


        return BaseResponse.onSuccess(adminFacade.getPayments(page, size, searchType,searchQuery, dateFrom, dateTo, overdueOnly));
    }

    //2-2) 배송 스케줄 관리
    @Operation(summary = "배송 스케줄 관리 조회", description = "페이지 별로 반납 및 배송 예약 일정을 조회합니다.")
    @GetMapping("/delivery-by-page")
    public BaseResponse<Page<GetDeliveryListRes>> getDeliveryScheduleListByPage(    @RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size,
                                                          @RequestParam(required = false) String searchType,
                                                          @RequestParam(required = false) String searchQuery,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
                                                          @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        Page<GetDeliveryListRes> dto = adminFacade.findAllDeliveryByPage(page, size, searchType, searchQuery, dateFrom, dateTo);
        return BaseResponse.onSuccess(dto);

    }

    @Operation(summary = "배송 스케줄 관리 조회", description = "반납 및 배송 예약 일정을 조회합니다.")
    @GetMapping("/delivery")
    public BaseResponse<List<GetDeliveryListRes>> getDeliveryScheduleList() {
        List<GetDeliveryListRes> dto = adminFacade.findAllDelivery();
        return BaseResponse.onSuccess(dto);

    }

    @Operation(summary = "관리자 배송 상태 업데이트 (배송준비중->배송중)", description = "관리자가 배송 요청의 상태를 업데이트 합니다.")
    @GetMapping("/delivery/reparing-complete/{deliveryIdx}")
    public BaseResponse<?> updateDeliveryStatus(@PathVariable Long deliveryIdx) {
        adminFacade.updateDeliveryStatus(deliveryIdx);

        return BaseResponse.onSuccess(null);
    }


    //2-3) 반납/수리 요청 관리
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
    public BaseResponse<?> updateReturnRequest(@PathVariable Long returnDeliveryIdx,
                                               @RequestBody @Valid PostAdminReturnDeliveryReq dto) {
        adminFacade.updateReturnRequest(returnDeliveryIdx, dto);
        return BaseResponse.onSuccess(null);

    }

    @Operation(summary = "관리자 수리 상태 업데이트 (수락/거절)", description = "관리자가 반납 요청의 상태를 업데이트 합니다.")
    @PostMapping("/repair/{returnDeliveryIdx}")
    public BaseResponse<?> updateRepairRequest(@PathVariable Long returnDeliveryIdx,
                                               @RequestBody PostAdminReturnDeliveryReq dto) {
        adminFacade.updateRepairRequest(returnDeliveryIdx,dto);

        return BaseResponse.onSuccess(null);
    }



    //3. 사용자 관리
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




    //4. 통계 리포트 페이지
    @Operation(summary = "관리자 통계페이지 카드뷰 조회", description = "관리자 통계페이지 카드뷰를 조회합니다.")
    @GetMapping("/statistics/cardview")
    public BaseResponse<GetAdminStatisticsCardRes> getStatisticsCardView() {
        GetAdminStatisticsCardRes dto = adminFacade.findStatisticsCardView();
        return BaseResponse.onSuccess(dto);
    }

    @Operation(summary = "관리자 통계페이지 차트 조회", description = "관리자 통계페이지 차트 데이터를 조회합니다.")
    @GetMapping("/statistics")
    public BaseResponse<GetAdminStatisticsRes> getStatistics() {
        GetAdminStatisticsRes dto = adminFacade.findStatistics();
        return BaseResponse.onSuccess(dto);
    }


    @Operation(summary = "관리자 통계페이지 최근 가입 유저 조회", description = "관리자 통계페이지에서 최근 가입한 5명의 유저를 조회합니다.")
    @GetMapping("/statistics/recent-users")
    public BaseResponse<List<GetAdminRecentUserRes>> getAdminRecentUsers() {
        List<GetAdminRecentUserRes> dto = adminFacade.getAdminRecentUsers();
        return BaseResponse.onSuccess(dto);
    }
    @Operation(summary = "관리자 통계페이지 최근 결제 조회", description = "관리자 통계페이지에서 최근 결제한 5명의 유저를 조회합니다.")
    @GetMapping("/statistics/recent-payments")
    public BaseResponse<List<GetAdminRecentPaymentRes>> getAdminRecentPayments() {
        List<GetAdminRecentPaymentRes> dto = adminFacade.getAdminRecentPayments();
        return BaseResponse.onSuccess(dto);
    }







    //TODO: 추후 구현할지 말지 고민중
//    @Operation(summary = "사용자 정보 업데이트", description = "관리자용 사용자 정보 수정 기능입니다.")
//    @PostMapping("/users")
//    public void updateUser() {
//    }




}
