package com.example.monthlylifebackend.subscribe.service;

import com.example.monthlylifebackend.common.code.status.ErrorStatus;
import com.example.monthlylifebackend.common.exception.handler.SubcribeHandler;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.subscribe.repository.*;
import com.example.monthlylifebackend.support.repository.PaymentRepository;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
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
    private final PaymentRepository paymentRepository;
    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final RentalDeliveryRepository rentalDeliveryRepository;
    private final SubscribeDetailRepository subscribeDetailRepository;

    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size) {
        return subscribeRepository.findDeliveryList(PageRequest.of(page, size));
    }

    @Transactional
    public void createSubscription(PostRentalDeliveryReqDto reqDto, String id) {
        // 유저 존재 여부 확인
        User user = userRepository.findById(id)
                .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_USER));

        Payment payment = Payment.builder().cardNumber(123123).build();
        paymentRepository.save(payment);

        Subscribe subscribe = subscribeMapper.tosubscribe(user, payment, reqDto.getProducts().get(0));
        subscribeRepository.save(subscribe);

        for (ProductRequestDto product : reqDto.getProducts()) {
            // 세일 존재 여부 확인
            Sale sale = saleRepository.findWithSalePricesByIdx(product.getSale_idx())
                    .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SALE));

            // 세일 가격 존재 여부 확인
            SalePrice price = salePriceRepository.findBySaleIdxAndPeriod(product.getSale_idx(), product.getPeriod())
                    .orElseThrow(() -> new SubcribeHandler(ErrorStatus._NOT_FOUND_SALE_PRICE));

            SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe, product, sale, price);
            subscribeDetail.setSubscribe(subscribe);
            subscribe.getSubscribeDetailList().add(subscribeDetail);

            RentalDelivery delivery = subscribeMapper.toRentalDelivery(reqDto, subscribeDetail);
            rentalDeliveryRepository.save(delivery);
        }
    }

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

    public List<GetSubscribeRes> getSubscriptionInfo(User user) {
        List<Subscribe> subscribes = subscribeRepository.findWithDetailsByUserId(user.getId());
        return subscribeMapper.toGetSubscribeResList(subscribes);
    }

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

    public GetSubscribeDetailInfoRes getReturnDelivery(String userId, Long detailId) {
        SubscribeDetail rs = getSubscribeDetailWithUserValidation(userId, detailId);
        return subscribeMapper.toReturnDeliveryDto(rs);
    }
}
