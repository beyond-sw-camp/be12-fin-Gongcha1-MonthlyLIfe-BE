package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.product.model.Sale;
import com.example.monthlylifebackend.product.model.SalePrice;
import com.example.monthlylifebackend.product.repository.ProductRepository;
import com.example.monthlylifebackend.product.repository.SaleHasProductRepository;
import com.example.monthlylifebackend.product.repository.SalePriceRepository;
import com.example.monthlylifebackend.product.repository.SaleRepository;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.subscribe.model.Payment;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.repository.SubscribeDetailRepository;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
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
