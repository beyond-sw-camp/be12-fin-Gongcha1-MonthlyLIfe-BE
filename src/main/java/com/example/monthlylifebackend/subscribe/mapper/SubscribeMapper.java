package com.example.monthlylifebackend.subscribe.mapper;


import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.product.model.Product;
import com.example.monthlylifebackend.sale.model.Sale;
import com.example.monthlylifebackend.sale.model.SaleHasProduct;
import com.example.monthlylifebackend.sale.model.SalePrice;
import com.example.monthlylifebackend.subscribe.dto.req.PostRentalDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.req.PostReturnDeliveryReq;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailInfoRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeDetailRes;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribePageResDto;
import com.example.monthlylifebackend.subscribe.model.RentalDelivery;
import com.example.monthlylifebackend.subscribe.model.Subscribe;
import com.example.monthlylifebackend.subscribe.model.SubscribeDetail;
import com.example.monthlylifebackend.subscribe.dto.res.GetSubscribeRes;
import com.example.monthlylifebackend.subscribe.model.*;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring") // Spring Bean으로 등록
public interface SubscribeMapper {

    @Mapping(source = "sale.name", target = "saleName")
    @Mapping(source = "salePrice.period", target = "period")
    @Mapping(source = "salePrice.price", target = "price")
    @Mapping(source = "user.name", target = "name")
    @Mapping(source = "user.email", target = "email")
    GetSubscribePageResDto getSubscriptionResDto(Sale sale, SalePrice salePrice, User user);

    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "billingKey", target = "billingKey")
    Subscribe tosubscribe(User user , BillingKey billingKey);


    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "salePrice.price", target = "price")
    @Mapping(source = "salePrice.period", target = "period")
    @Mapping(source = "salePrice.sale", target = "sale")
    @Mapping(source = "subscribe", target = "subscribe")
    @Mapping(target = "startAt", expression = "java(java.time.LocalDateTime.now())")  // start_at에 현재 시간 적용
    @Mapping(target = "endAt", expression = "java(calculateEndAt(java.time.LocalDateTime.now(), salePrice.getPeriod()))")  // endAt 계산
    SubscribeDetail tosubscribedetail(Subscribe subscribe, SalePrice salePrice);

    // 개월 수 더하는 로직
    default LocalDateTime calculateEndAt(LocalDateTime start_at, int period) {
        return start_at.plusMonths(period);  // period만큼 월을 더해 계산
    }




    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "dto.recipientName", target = "recipientName")
    @Mapping(source = "dto.recipientPhone", target = "recipientPhone")
    @Mapping(source = "dto.postalCode", target = "postalCode")
    @Mapping(source = "dto.address1", target = "address1")
    @Mapping(source = "dto.address2", target = "address2")
    @Mapping(source = "dto.deliveryMemo", target = "deliveryMemo")
    @Mapping(source = "subscribeDetail", target = "subscribeDetail") // 예시: subscribeDetail 설정은 별도로 처리
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    RentalDelivery toRentalDelivery(PostRentalDeliveryReq dto , SubscribeDetail subscribeDetail);



//    List<GetSubscribeRes> getSubscriptionInfo(List<Subscribe> subscribes);




    @Mapping(source = "idx", target = "subscribeIdx")
    @Mapping(source = "createdAt", target = "createdAt")
    @Mapping(source = "subscribeDetailList", target = "details")
    GetSubscribeRes toGetSubscribeRes(Subscribe subscribe);

    @Mapping(source = "idx", target = "idx")
    @Mapping(source = "sale.name", target = "name")  // Join fetch 한 Sale.name
    @Mapping(source = "period", target = "period")
    @Mapping(source = "price", target = "price")
    GetSubscribeDetailRes toGetSubscribeDetailRes(SubscribeDetail detail);

    List<GetSubscribeRes> toGetSubscribeResList(List<Subscribe> subscribes);
    List<GetSubscribeDetailRes> toGetSubscribeDetailResList(List<SubscribeDetail> details);


    @Mapping(target = "idx", ignore = true)
    @Mapping(source = "detail", target = "subscribeDetail")
    ReturnDelivery toReturnDeliveryEntity(SubscribeDetail detail, PostReturnDeliveryReq postReturnDeliveryReq) ;


    // 구독 반납
    @Mapping(source = "idx", target = "subscribeDetailIdx")
    @Mapping(source = "sale.name", target = "salename")
    @Mapping(target = "imageUrl", expression = "java(getFirstImageUrl(detail))")
    @Mapping(source = "status", target = "status")
    GetSubscribeDetailInfoRes toReturnDeliveryDto(SubscribeDetail detail);



    default String getFirstImageUrl(SubscribeDetail detail) {
        List<SaleHasProduct> shpList = detail.getSale().getSaleHasProductList();
        if (shpList != null && !shpList.isEmpty()) {
            Product product = shpList.get(0).getProduct();
            if (product.getProductImageList() != null && !product.getProductImageList().isEmpty()) {
                return product.getProductImageList().get(0).getProductImgUrl();
            }
        }
        return null;
    }

}
