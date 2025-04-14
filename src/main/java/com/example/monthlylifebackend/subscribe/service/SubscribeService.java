package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
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


    @Transactional
    public void createSubcription(PostRentalDeliveryReqDto reqDto , String id) {


        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Payment payment = Payment.builder().cardNumber(123123123).build();
        paymentRepository.save(payment);


        Subscribe subscribe = subscribeMapper.tosubscribe(user, payment);
        subscribeRepository.save(subscribe);


        Sale sale=  saleRepository.findByIdx(reqDto.getSale_idx())
                .orElseThrow(() -> new RuntimeException("렌탈 상품 없음"));


        SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe ,reqDto );
        subscribeDetailRepository.save(subscribeDetail);


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
