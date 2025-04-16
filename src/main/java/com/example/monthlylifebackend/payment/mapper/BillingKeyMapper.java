package com.example.monthlylifebackend.payment.mapper;

import com.example.monthlylifebackend.payment.dto.req.PostBillingKeyReq;
import com.example.monthlylifebackend.payment.model.BillingKey;
import com.example.monthlylifebackend.user.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BillingKeyMapper {
     BillingKey toEntity(PostBillingKeyReq dto, User user);
}
