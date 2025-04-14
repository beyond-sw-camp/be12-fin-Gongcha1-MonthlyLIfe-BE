package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.product.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.product.repository.SalePriceRepository;
import com.example.monthlylifebackend.product.repository.SaleRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.Payment;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.repository.SubscribeDetailRepository;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
 import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.example.monthlylifebackend.support.repository.PaymentRepository;
import com.example.monthlylifebackend.support.repository.RentalDeliveryRepository;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class SubscribeService {




    private final SubscribeRepository subscribeRepository;

    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size) {
        Page<GetDeliveryListRes> pagedto = (Page<GetDeliveryListRes>) subscribeRepository.findDeliveryList(PageRequest.of(page,size));
        return pagedto;
    }
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SaleHasProductRepository saleHasProductRepository;
    private final SubscribeMapper subscribeMapper;
    private final SaleRepository saleRepository;
    private final SalePriceRepository salePriceRepository;
    private final PaymentRepository paymentRepository;
    private final RentalDeliveryRepository rentalDeliveryRepository;
    private final SubscribeDetailRepository subscribeDetailRepository;

    //구독 할때 결제 할때
    @Transactional
    public void createSubcription(PostRentalDeliveryReqDto reqDto, String id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        // Payment 저장
        Payment payment = Payment.builder().cardNumber(123123).build();
        paymentRepository.save(payment);

        // Sale 조회
        Sale sale = saleRepository.findByIdx(reqDto.getSale_idx())
                .orElseThrow(() -> new RuntimeException("해당 세일 없음"));

        // Subscribe 생성
        Subscribe subscribe = subscribeMapper.tosubscribe(user, payment, reqDto);

        // SubscribeDetail 생성 및 Subscribe에 연결
        SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe, reqDto, sale);

        subscribe.getSubscribeDetailList().add(subscribeDetail);

        // Subscribe 저장 (Cascade ALL에 의해 subscribeDetail도 함께 persist됨)
        subscribeRepository.save(subscribe);

        // RentalDelivery 생성
        RentalDelivery rs = subscribeMapper.toRentalDelivery(reqDto, subscribeDetail);
        rentalDeliveryRepository.save(rs);
    }




    public GetSubscribePageResDto getSubscription(String id, Long saleidx , int period) {
        Sale sale = saleRepository.findById(saleidx)
                .orElseThrow(() -> new RuntimeException("해당 세일 없음"));

        SalePrice salePrice = salePriceRepository.findBySaleIdxAndPeriod(saleidx, period)
                .orElseThrow(() -> new RuntimeException("해당 기간 가격 없음"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저 없음"));


        return subscribeMapper.getSubscriptionResDto(sale, salePrice ,user);
    }
}
