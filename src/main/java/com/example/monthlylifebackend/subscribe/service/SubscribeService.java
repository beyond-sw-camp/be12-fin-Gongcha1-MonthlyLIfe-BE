package com.example.monthlylifebackend.subscribe.service;


import com.example.monthlylifebackend.sale.repository.SalePriceRepository;
import com.example.monthlylifebackend.sale.repository.SaleRepository;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.req.*;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReqDto;
import com.example.monthlylifebackend.subscribe.dto.req.ProductRequestDto;
import com.example.monthlylifebackend.subscribe.dto.response.GetDeliveryListRes;
import com.example.monthlylifebackend.subscribe.mapper.SubscribeMapper;
import com.example.monthlylifebackend.payment.model.Payment;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.subscribe.repository.ReturnDeliveryRepository;
import com.example.monthlylifebackend.subscribe.repository.SubscribeDetailRepository;
import com.example.monthlylifebackend.subscribe.model.Payment;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.repository.SubscribeRepository;
 import lombok.RequiredArgsConstructor;
import com.example.monthlylifebackend.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import com.example.monthlylifebackend.payment.PaymentRepository;
import com.example.monthlylifebackend.support.repository.RentalDeliveryRepository;
import com.example.monthlylifebackend.user.model.User;
import com.example.monthlylifebackend.support.repository.PaymentRepository;
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

    private final PaymentRepository paymentRepository;

    private final ReturnDeliveryRepository returnDeliveryRepository;
    private final RentalDeliveryRepository rentalDeliveryRepository;
    private final SubscribeDetailRepository subscribeDetailRepository;


    public Page<GetDeliveryListRes> findDeliveryListByPage(int page, int size) {
        Page<GetDeliveryListRes> pagedto = (Page<GetDeliveryListRes>) subscribeRepository.findDeliveryList(PageRequest.of(page,size));
        return pagedto;
    }

    //구독 할때 결제 할때
    @Transactional
    public void createSubscription(PostRentalDeliveryReqDto reqDto, String id) {


        // Todo list -> 나중에 생각해야하는거 일단 단일 여러개의 제품을 받아서 상품 구매 까지는
        //  가능은한데 패키지? 는 나중에 생각해야할수도있음 .


        // 임시 유저 and 페이먼트 임시 생성이라 지워도됌 ***********************
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저 없음"));

        Payment payment = Payment.builder().build();
        paymentRepository.save(payment);
        // *************************


        Subscribe subscribe = subscribeMapper.tosubscribe(user, payment, reqDto.getProducts().get(0));
        subscribeRepository.save(subscribe);


        for (ProductRequestDto product : reqDto.getProducts()) {
            Sale sale = saleRepository.findWithSalePricesByIdx(product.getSale_idx())
                    .orElseThrow(() -> new RuntimeException("해당 세일 없음"));
            SalePrice price = salePriceRepository.findBySaleIdxAndPeriod(product.getSale_idx(), product.getPeriod())
                    .orElseThrow(() -> new RuntimeException("해당 가격 없음"));


            SubscribeDetail subscribeDetail = subscribeMapper.tosubscribedetail(subscribe, product, sale, price);
            // Todo list 셋 해야할까?


            subscribeDetail.setSubscribe(subscribe);


            subscribe.getSubscribeDetailList().add(subscribeDetail);


            RentalDelivery delivery = subscribeMapper.toRentalDelivery(reqDto, subscribeDetail);
            rentalDeliveryRepository.save(delivery);
        }
    }

    // 구독버튼 누를시  필요로 하는 정보들 가져오는 코드
    public GetSubscribePageResDto getSubscription(String id, Long saleidx, int period) {
        Sale sale = saleRepository.findById(saleidx)
                .orElseThrow(() -> new RuntimeException("해당 세일 없음"));

        SalePrice salePrice = salePriceRepository.findBySaleIdxAndPeriod(saleidx, period)
                .orElseThrow(() -> new RuntimeException("해당 기간 가격 없음"));

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("유저 없음"));


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

        if (detail.getStatus().equals(RETURN_REQUESTED)) {
            throw new RuntimeException("현재 반납 요청이 불가능한 상태입니다.");
        }

        detail.updateStatus(RETURN_REQUESTED);


        ReturnDelivery delivery = subscribeMapper.toReturnDeliveryEntity(detail, reqDto);
        returnDeliveryRepository.save(delivery);




    }

    // 사용자에게 상품이 있는지 없는지 확인하는 코드
    public SubscribeDetail getSubscribeDetailWithUserValidation(String userId, Long detailIdx) {
        SubscribeDetail detail = subscribeDetailRepository.findWithProductAndUser(detailIdx, userId)
                .orElseThrow(() -> new RuntimeException("구독 상세 없음"));

        if (!detail.getSubscribe().getUser().getId().equals(userId)) {
            throw new RuntimeException("권한 없음");
        }
        return detail;
    }


    public GetSubscribeDetailInfoRes getReturnDelivery(String userId, Long detailId) {
        SubscribeDetail rs = getSubscribeDetailWithUserValidation(userId, detailId);
        return subscribeMapper.toReturnDeliveryDto(rs);
    }
}
