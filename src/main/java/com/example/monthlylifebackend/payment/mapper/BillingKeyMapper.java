package com.example.monthlylifebackend.payment.mapper;

import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.dto.res.GetPaymentMethodRes;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.user.model.User;
import io.portone.sdk.server.payment.billingkey.BillingKeyInfo;

import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface BillingKeyMapper {
     BillingKey toEntity(PostBillingKeyReq dto, User user);
     BillingKey toEntity(BillingKeyInfo info, User user);

     GetPaymentMethodRes toDto(BillingKey entity);
     default Page<GetPaymentMethodRes> toDtoPage(Page<BillingKey> entities) {
          return entities.map(this::toDto);
     }
}
