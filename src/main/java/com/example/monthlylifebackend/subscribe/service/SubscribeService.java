package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SubcribeHandler;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.SubscribeAndSalesDto;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.*;
import com.example.monthlylifebackend.subscribe.dto.req.PostSaleReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.mapper.RentalMapper;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.subscribe.repository.*;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import com.example.monthlylifebackend.subscribe.repository.RentalDeliveryRepository;
import org.springframework.data.domain.Page;
import com.example.monthlylifebackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.monthlylifebackend.subscribe.model.SubscribeStatus.*;

@Service
@RequiredArgsConstructor
public class SubscribeService {


    private final SubscribeRepository subscribeRepository;


    private final UserRepository userRepository;
    private final RepairRequestRepository repairRequestRepository;
    private final SubscribeMapper subscribeMapper;
    private final RentalMapper rentalMapper;
    private final SaleRepository saleRepository;

    private final SalePriceRepository salePriceRepository;

    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final RentalDeliveryRepository rentalDeliveryRepository;
    private final SubscribeDetailRepository subscribeDetailRepository;

    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size,
                                                           String searchType, String searchQuery,
                                                           LocalDate dateFrom, LocalDate dateTo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        LocalDateTime from = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDateTime to = dateTo != null ? dateTo.atTime(23, 59, 59) : null;

        return subscribeRepository.findDeliveryListByPage(pageable, searchType, searchQuery, from, to);
    }
    public List<GetDeliveryListRes> findDeliveryList() {
        return subscribeRepository.findDeliveryList();
    }

    //구독 할때
    @Transactional
    public SubscribeAndSalesDto createSubscription(PostSubscribeReq reqDto, User user) {
        //결제 수단
        BillingKey billingKey = BillingKey.builder().idx(reqDto.getBillingKeyIdx()).build();

        Subscribe subscribe = subscribeMapper.tosubscribe(user, billingKey);
        List<SalePrice> salePriceList = new ArrayList<>();
        for (PostSaleReq saleReq : reqDto.getSales()) {
            // 세일 가격 불러오기
            SalePrice salePrice = salePriceRepository.findBySaleIdxAndPeriod(saleReq.getSaleIdx(), saleReq.getPeriod())
                    .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));
            salePriceList.add(salePrice);
            SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe, salePrice);
            subscribe.getSubscribeDetailList().add(subscribeDetail);

            RentalDelivery delivery = subscribeMapper.toRentalDelivery(reqDto.getRentalDelivery(), subscribeDetail);
            rentalDeliveryRepository.save(delivery);
        }

        SubscribeAndSalesDto ret = new SubscribeAndSalesDto();
        ret.setSubscribe(subscribeRepository.save(subscribe));
        ret.setSalePriceList(salePriceList);
        return ret;
    }

    // 구독버튼 누를시  필요로 하는 정보들 가져오는 코드
    public GetSubscribePageResDto getSubscription(String id, Long saleidx, int period) {
        // 세일 존재 여부 확인
        Sale sale = saleRepository.findById(saleidx)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SUBSCRIBE));

        // 세일 가격 존재 여부 확인
        SalePrice salePrice = salePriceRepository.findBySaleIdxAndPeriod(saleidx, period)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));

        // 유저 존재 여부 확인
        User user = userRepository.findById(id)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_USER));

        return subscribeMapper.getSubscriptionResDto(sale, salePrice, user);
    }


    public Page<GetSubscribeListRes> getSubscriptionInfo(String userId, Pageable pageable) {
        // 1) 구독ID 페이징 → 각 페이지에 N개 ID만
        Page<Long> idPage = subscribeRepository.findSubscriptionIdsByUser(userId, pageable);

        // 2) 그 ID들로 상세 조회
        List<GetSubscribeListProjection> projections =
                subscribeRepository.findDetailsBySubscribeIds(userId, idPage.getContent());

        // 3) Projection → DTO 그룹핑
        List<GetSubscribeListRes> dtoList = subscribeMapper.toResList(projections);

        // 4) PageImpl 으로 감싸기
        return new PageImpl<>(
                dtoList,
                pageable,
                idPage.getTotalElements()
        );
    }
    // 반납 시 생성되는 반납 신청서
    public void createReturnDelivery(String userId, PostReturnDeliveryReq reqDto) {
        SubscribeDetail detail = getSubscribeDetailWithUserValidation(userId, reqDto.getSubscribedetailIdx());

        // 반납 요청 상태 확인
        if (detail.getStatus().equals(RETURN_REQUESTED)) {
            throw new SubcribeHandler(ErrorStatus._INVALID_SUBSCRIBE_STATUS);
        }

        detail.updateStatus(RETURN_REQUESTED);

        ReturnDelivery delivery = subscribeMapper.toReturnDeliveryEntity(detail, reqDto);
        returnDeliveryRepository.save(delivery);
    }

    // 사용자에게 상품이 있는지 없는지 확인하는 코드
    public SubscribeDetail getSubscribeDetailWithUserValidation(String userId, Long detailIdx) {
        // 구독 상세 존재 여부 확인
        SubscribeDetail detail = subscribeDetailRepository.findWithProductAndUser(detailIdx, userId)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SUBSCRIBE_DETAIL));

        // 구독 접근 권한 확인
        if (!detail.getSubscribe().getUser().getId().equals(userId)) {
            throw new SubcribeHandler(ErrorStatus._UNAUTHORIZED_SUBSCRIBE_ACCESS);
        }
        return detail;
    }



    // 구독 상세 정보 가져와짐
    public GetSubscribeDetailInfoRes getReturnDelivery(String userId, Long detailId) {
        SubscribeDetail rs = getSubscribeDetailWithUserValidation(userId, detailId);
        return subscribeMapper.toReturnDeliveryDto(rs);
    }

    //구독 idx로 구독 반환
    public Subscribe getSubscribeByIdx(Long idx) {
        Subscribe subscribe = subscribeRepository.findById(idx).orElseThrow();

        return subscribe;
    }

    public Long calcPriceCycle(Subscribe subscribe, int cycle ) {
        Long price = 0L;
        for(SubscribeDetail sd : subscribe.getSubscribeDetailList()) {
            if(sd.getPeriod() >= cycle) {
                price += sd.getPrice();
            }
        }
        return price;
    }


    public void undoCancleSubscription(String userid, Long detailIdx) {

        SubscribeDetail detail = subscribeDetailRepository.findById(detailIdx)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SUBSCRIBE_DETAIL));
        if (!detail.getSubscribe().getUser().getId().equals(userid)) {
            throw new SubcribeHandler(ErrorStatus._AccessDenied_);
        }
        if (detail.getStatus() != SubscribeStatus.RETURN_REQUESTED) {
            throw new SubcribeHandler(ErrorStatus._INVALID_RETURN_STATUS);

        }

        ReturnDelivery delivery = returnDeliveryRepository.findLatestByDetailIdx(detailIdx)
                .orElseThrow(() -> new SubcribeHandler (ErrorStatus._NOT_FOUND_RETURN_));
        if (delivery.getStatus() != ReturnDeliveryStatus.RETURN_REQUESTED) {
            throw new IllegalStateException("이미 처리된 반납 요청입니다.");
        }

        subscribeDetailRepository.updateStatus(detailIdx, SubscribeStatus.SUBSCRIBING);
        returnDeliveryRepository.updateStatusByDetailIdx(detailIdx,
                ReturnDeliveryStatus.RETURN_CANCELED,
                LocalDateTime.now()
        );


    }

    public Page<GetAdminSubscribeRes> getAdminSubscribeByPage(int page, int size,
                                                              String keyword,
                                                              String status,
                                                              Integer minMonths,
                                                              Integer maxMonths) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        SubscribeStatus statusEnum = null;
        if (status != null && !status.isBlank()) {
             statusEnum = SubscribeStatus.valueOf(status);
        }

        return subscribeRepository.findAdminSubscribe(pageable, keyword, statusEnum, minMonths, maxMonths);
    }

    public List<GetAdminSubscribeDetailRes> getAdminSubscribeDetail(Long subscribeId) {
        List<GetAdminSubscribeDetailRes> dto = subscribeRepository.findAdminSubscribeDetail(subscribeId);

        return dto;
    }

    public void extendSubscription(Long detailIdx, String userId, PostExtendRequest dto) {
        SubscribeDetail detail = subscribeDetailRepository.findWithProductAndUser(detailIdx, userId)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SUBSCRIBE_DETAIL));
        LocalDateTime newStartAt = detail.getEndAt();
        LocalDateTime newEndAt = newStartAt.plusMonths(dto.getExtentPeriod());
        detail.updateStatus(RESERVED);
        SubscribeDetail extended = subscribeMapper.toExtendSubscription(detail, newStartAt, newEndAt);
        subscribeDetailRepository.save(extended);



    }

    public void createRepairOrLost(PostRepairOrLostReq req , String userId) {
        SubscribeDetail detail = subscribeDetailRepository.findWithProductAndUser(req.getSubscribeDetailIdx(), userId)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SUBSCRIBE_DETAIL));

        RepairRequest rr = subscribeMapper.toEntity(req, detail);
        if (req.getType() == ReportType.REPAIR) {
            req.getImageUrls().stream()
                    .filter(u -> u != null && !u.isBlank())
                    .map(u -> RepairImage.builder()
                            .url(u)
                            .repairRequest(rr)   // rr 을 참조
                            .build())
                    .forEach(rr.getRepairImageList()::add);
            detail.updateStatus(REPAIR_REQUESTED);
        } else if (req.getType() == ReportType.LOST) {
            detail.updateStatus(LOST);

        }
        repairRequestRepository.save(rr);


        ReturnDelivery delivery = subscribeMapper.toReturnDeliveryRepair(detail);
        returnDeliveryRepository.save(delivery);
    }

    public GetRentalDeliveryInfoRes getSubsribeDelivery(String userId, Long subscribeDetailIdx) {



        // 검증 통과했으면 배송정보 가져오기
        RentalDelivery rs = rentalDeliveryRepository.findLatestRentalDeliveryByUserIdAndDetailIdx(userId,subscribeDetailIdx);

        if (rs == null) {
            throw new SubcribeHandler(ErrorStatus._NOT_FOUND_ACCESS_RENTAL_INFO_);
        }

        LocalDateTime shippedAt = rs.getShippedAt(); // 출발 시각
        LocalDateTime estimatedDeliveredAt = null;
        if (shippedAt != null) {
            estimatedDeliveredAt = shippedAt.plusHours(2);
        }

        // 3) 현재 상태 판단
        String currentStatus;
        LocalDateTime now = LocalDateTime.now();
        if (shippedAt == null) {

        } else if (now.isBefore(estimatedDeliveredAt)) {
            rs.updatedstatus(RentalStatus.SHIPPING);
        } else {
            rs.updatedstatus(RentalStatus.DELIVERED);
        }


        return rentalMapper.toDto(rs);
    }
}
