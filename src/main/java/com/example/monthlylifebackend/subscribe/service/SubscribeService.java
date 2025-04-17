package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SubcribeHandler;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.req.PostSaleReq;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.subscribe.repository.ReturnDeliveryRepository;
import com.example.monthlylifebackend.subscribe.repository.SubscribeDetailRepository;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import com.example.monthlylifebackend.subscribe.repository.RentalDeliveryRepository;
import com.example.monthlylifebackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.monthlylifebackend.subscribe.model.SubscribeStatus.RETURN_REQUESTED;

@Service
@RequiredArgsConstructor
public class SubscribeService {


    private final SubscribeRepository subscribeRepository;


    private final UserRepository userRepository;

    private final SubscribeMapper subscribeMapper;

    private final SaleRepository saleRepository;

    private final SalePriceRepository salePriceRepository;

    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final RentalDeliveryRepository rentalDeliveryRepository;
    private final SubscribeDetailRepository subscribeDetailRepository;

    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size) {
        return subscribeRepository.findDeliveryListByPage(PageRequest.of(page, size));
    }
    public List<GetDeliveryListRes> findDeliveryList() {
        return subscribeRepository.findDeliveryList();
    }

    //구독 할때
    @Transactional
    public Subscribe createSubscription(PostSubscribeReq reqDto, User user) {
        //결제 수단
        BillingKey billingKey = BillingKey.builder().idx(reqDto.getBillingKeyIdx()).build();

        Subscribe subscribe = subscribeMapper.tosubscribe(user, billingKey);

        for (PostSaleReq saleReq : reqDto.getSales()) {
            // 세일 가격 불러오기
            SalePrice salePrice = salePriceRepository.findBySaleIdxAndPeriod(saleReq.getSale_idx(), saleReq.getPeriod())
                    .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));

            SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe, salePrice);
            subscribe.getSubscribeDetailList().add(subscribeDetail);

            RentalDelivery delivery = subscribeMapper.toRentalDelivery(reqDto.getRentalDelivery(), subscribeDetail);
            rentalDeliveryRepository.save(delivery);
        }
        Subscribe ret = subscribeRepository.save(subscribe);

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

    // 나의 구독 정보들
    public List<GetSubscribeRes> getSubscriptionInfo(User user) {
        List<Subscribe> subscribes = subscribeRepository.findWithDetailsByUserId(user.getId());
        return subscribeMapper.toGetSubscribeResList(subscribes);
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
}
